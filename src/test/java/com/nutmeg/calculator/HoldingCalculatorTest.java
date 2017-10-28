package com.nutmeg.calculator;


import com.nutmeg.calculator.impl.HoldingCalculatorImpl;
import com.nutmeg.types.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class HoldingCalculatorTest {

    private final HoldingCalculator holdingCalculator = new HoldingCalculatorImpl();


    @Test
    public void validateFile() throws URISyntaxException {

        final URI uri = new URI(getClass().getClassLoader().getResource("Transactions.csv").toString());

        final File file = new File(uri);

        holdingCalculator.calculateHoldings(file, LocalDate.parse("20170101", DateTimeFormatter.ofPattern("yyyyMMdd")));


    }


    @ParameterizedTest
    @CsvSource({
            "NEAA0000,20170101,DEP,100,1,CASH, 100",
            "NEAA0000,20170102,BOT,20,2.123,VUKE, 57.54 ",
            "NEAA0000,20170102,BOT,30,1.500,VUSA, 12.54",
            "NEAA0000,20170201,DIV,0.2024,1,VUKE, 12.7424",
            "NEAA0000,20170201,SLD,20,2.000,VUSA, 52.7424",
            "NEAA0000,20170201,BOT,10.512,3.3350,GILS, 17.6848",
            "NEAB0001,20161201,DEP,10000,1,CASH, 10000.0000",
            "NEAB0001,20170301,WDR,5000,1,CASH, 5000.0000"
    })
    public void calculateAccountCashGivenSetOfTransaction(final String account, final Date date, final TransactionType transactionType, final Double units, final Double price, final String asset, final Double cash) {

    }


    @ParameterizedTest
    @CsvSource({
            "NEAA0000,20170101,DEP,100,1,CASH, 0",
            "NEAA0000,20170102,BOT,20,2.123,VUKE, 20 ",
            "NEAA0000,20170102,BOT,30,1.500,VUSA, 30",
            "NEAA0000,20170201,DIV,0.2024,1,VUKE, 20",
            "NEAA0000,20170201,SLD,20,2.000,VUSA, 10",
            "NEAA0000,20170201,BOT,10.512,3.3350,GILS, 10.512"
    })
    public void calculateAccountStockHoldingGivenSetOfTransaction(final String account, final Date date, final TransactionType transactionType, final Double units, final Double price, final String asset, final Double stocks) {

    }

}