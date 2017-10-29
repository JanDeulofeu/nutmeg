package com.nutmeg.service;

import com.nutmeg.annotations.ThreadSafe;
import com.nutmeg.model.Holding;
import com.nutmeg.model.Stock;
import com.nutmeg.repository.HoldingRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@ThreadSafe
public final class StockConverter {

    private static final int NUMBER_DECIMALS = 4;


    public void convert(final List<Stock> stocks) {

        for (final Stock stock : stocks) {

            final Holding holdingStock = HoldingRepository.read(stock.getAccount(), stock.getAsset());
            final Holding holdingCash = HoldingRepository.read(stock.getAccount(), HoldingRepository.CASH);

            final double holdingCalculation;
            final double cashCalculation;

            switch (stock.getTransactionType()) {

                case BOT:
                    holdingCalculation = holdingStock.getHolding() + stock.getUnits();
                    insertHoldingIntoRepository(stock, holdingStock, holdingCalculation);

                    cashCalculation = Math.abs(stock.getUnits() * stock.getPrice() - holdingCash.getHolding());
                    insertCashIntoRepository(stock, holdingCash, cashCalculation);
                    break;

                case SLD:
                    holdingCalculation = Math.abs(holdingStock.getHolding() - stock.getUnits());
                    insertHoldingIntoRepository(stock, holdingStock, holdingCalculation);

                    cashCalculation = Math.abs(stock.getUnits() * stock.getPrice() + holdingCash.getHolding());
                    insertCashIntoRepository(stock, holdingCash, cashCalculation);
                    break;

                case DIV:
                    cashCalculation = Math.abs(stock.getUnits() * stock.getPrice() + holdingCash.getHolding());
                    insertCashIntoRepository(stock, holdingCash, cashCalculation);
                    break;

                case DEP:
                    cashCalculation = stock.getUnits() + holdingCash.getHolding();
                    insertCashIntoRepository(stock, holdingCash, cashCalculation);
                    break;

                case WDR:
                    cashCalculation = Math.abs(stock.getUnits() - holdingCash.getHolding());
                    insertCashIntoRepository(stock, holdingCash, cashCalculation);
                    break;
            }
        }
    }

    private void insertCashIntoRepository(final Stock stock, final Holding holdingCash, final double cashCalculation) {
        holdingCash.setHoldings(formatDigits(cashCalculation));
        HoldingRepository.write(holdingCash, stock.getAccount(), HoldingRepository.CASH);
    }

    private void insertHoldingIntoRepository(final Stock stock, final Holding holdingStock, final double calculation) {
        holdingStock.setHoldings(formatDigits(calculation));
        HoldingRepository.write(holdingStock, stock.getAccount(), stock.getAsset());
    }


    private double formatDigits(final Double value) {
        return new BigDecimal(Double.toString(value)).setScale(NUMBER_DECIMALS, RoundingMode.HALF_UP).doubleValue();
    }
}
