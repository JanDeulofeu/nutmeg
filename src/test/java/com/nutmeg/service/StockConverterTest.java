package com.nutmeg.service;

import com.nutmeg.model.Holding;
import com.nutmeg.model.Stock;
import com.nutmeg.repository.HoldingRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class StockConverterTest {


    private final StockConverter stockConverter = new StockConverter();


    @ParameterizedTest
    @CsvSource({
            "'NEAA0000,20170101,DEP,100,1,CASH', 100 , 100",
            "'NEAA0000,20170102,BOT,20,2.123,VUKE', 57.54, 20",
            "'NEAA0000,20170102,BOT,30,1.500,VUSA', 12.54 , 30",
            "'NEAA0000,20170201,DIV,0.2024,1,VUKE', 12.7424, 20",
            "'NEAA0000,20170201,SLD,20,2.000,VUSA', 52.7424, 10",
            "'NEAA0000,20170201,BOT,10.512,3.3350,GILS', 17.6849, 10.512",
    })
    public void validateConverterCalculateCash(final String stockString, final String cash, final String holdings) {

        final Stock stock = StockBuilder.buildStock(stockString);

        stockConverter.convert(Arrays.asList(stock));


        final Holding actual = HoldingRepository.read(stock);

        assertThat(Double.valueOf(holdings)).isEqualTo(actual.getHolding());
        assertThat(stock.getAsset()).isEqualTo(actual.getAsset());

        final Holding holding = HoldingRepository.readCash(stock.getAccount());
        assertThat(Double.valueOf(cash)).isEqualTo(holding.getHolding());

    }

}