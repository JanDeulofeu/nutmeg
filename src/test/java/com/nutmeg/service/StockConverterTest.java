package com.nutmeg.service;

import com.nutmeg.model.Holding;
import com.nutmeg.model.Stock;
import com.nutmeg.repository.HoldingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class StockConverterTest {

    static {
        HoldingRepository.reset();
    }


    @ParameterizedTest
    @CsvSource({
            "'NEAA0000,20170101,DEP,0,1,CASH', 0 , 0",
            "'NEAA0000,20170101,DEP,100,1,CASH', 100 , 100",
            "'NEAA0000,20170102,BOT,20,2.123,VUKE', 57.54, 20",
            "'NEAA0000,20170102,BOT,30,1.500,VUSA', 12.54 , 30",
            "'NEAA0000,20170201,DIV,0.2024,1,VUKE', 12.7424, 20",
            "'NEAA0000,20170201,SLD,20,2.000,VUSA', 52.7424, 10",
            "'NEAA0000,20170201,BOT,10.512,3.3350,GILS', 17.6849, 10.512"
    })
    public void validateConverterCalculateCash(final String stockString, final String cash, final String holdings) {

        final Stock stock = StockBuilder.buildStock(stockString);

        new StockConverter().convert(Arrays.asList(stock));


        final Holding actual = HoldingRepository.read(stock.getAccount(), stock.getAsset());
        final Holding holdingCash = HoldingRepository.read(stock.getAccount(), HoldingRepository.CASH);

        assertThat(Double.valueOf(holdings)).isEqualTo(actual.getHolding());
        assertThat(stock.getAsset()).isEqualTo(actual.getAsset());

        assertThat(Double.valueOf(cash)).isEqualTo(holdingCash.getHolding());
    }


    @Test
    public void validateHoldingsZeroAreNotAddedToTheOutput() {
        
        HoldingRepository.reset();

        final Stock stock = StockBuilder.buildStock("NEAA0000,20170102,BOT,0,0,VUKE");

        new StockConverter().convert(Arrays.asList(stock));

        final Map<String, List<Holding>> result = HoldingRepository.readAll();

        assertThat(result).isEmpty();

    }

}