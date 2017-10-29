package com.nutmeg.service;

import com.nutmeg.model.Stock;
import com.nutmeg.types.TransactionType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StockBuilder {


    public static final String SEPARATOR = ",";


    public static Stock buildStock(final String[] input) {

        return generateStock(input);
    }

    public static Stock buildStock(final String input) {

        return generateStock(input.split(SEPARATOR));
    }


    private static Stock generateStock(final String[] input) {
        return new Stock(input[0]
                , LocalDate.parse(input[1], DateTimeFormatter.ofPattern("yyyyMMdd"))
                , TransactionType.valueOf(input[2])
                , Double.valueOf(input[3])
                , Double.valueOf(input[4])
                , input[5]
        );
    }
}
