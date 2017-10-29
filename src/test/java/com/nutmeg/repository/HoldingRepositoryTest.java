package com.nutmeg.repository;

import com.nutmeg.model.Holding;
import com.nutmeg.model.Stock;
import com.nutmeg.service.StockBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class HoldingRepositoryTest {

    static {
        HoldingRepository.reset();
    }

    @ParameterizedTest
    @CsvSource({
            "'NEAA0000,20170101,DEP,100,1,CASH', 100",
            "'NEAA0000,20170101,DEP,20,1,CASH', 20",
            "'NEAA0000,20170101,DEP,150,1,CASH', 150",
            "'NEAA0000,20170201,DIV,0.2024,10,VUKE', 0.2024",
            "'NEAA0000,20170201,DIV,8,10,VUKE', 8",
            "'NEAA0000,20170102,BOT,20,2.123,VUKE', 20",
            "'NEAA0000,20170102,SLD,20,1,VUKE', 20",
    })
    public void validateHoldingMapValuesAreReplacedByAssetValue(final String stockString, final String value) {

        final Stock stock = StockBuilder.buildStock(stockString);
        final Holding holding = new Holding(stock.getAsset(), stock.getUnits());

        HoldingRepository.write(holding, stock.getAccount(), stock.getAsset());
        final Holding actual = HoldingRepository.read(stock.getAccount(), stock.getAsset());

        assertThat(actual.getHolding()).isEqualTo(Double.valueOf(value));
        assertThat(actual.getAsset()).isEqualTo(stock.getAsset());
    }


}