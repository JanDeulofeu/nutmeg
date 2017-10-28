package com.nutmeg.calculator.impl;

import com.nutmeg.calculator.HoldingCalculator;
import com.nutmeg.model.Holding;
import com.nutmeg.model.Stock;
import com.nutmeg.types.TransactionType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HoldingCalculatorImpl implements HoldingCalculator {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String SEPARATOR = ",";


    @Override
    public Map<String, List<Holding>> calculateHoldings(final File transactionFile, final LocalDate date) {

        final Map<String, List<Holding>> stockMap = new HashMap<>();


        try (final Stream<String> lines = Files.lines(transactionFile.toPath())) {

            final List<Stock> collect = lines.map(line -> line.split(SEPARATOR))
                    .map(this::buildStock)
                    .collect(Collectors.toList());
//                    .collect(Collectors.groupingBy(Stock::getAccount));


            collect.forEach(k -> {

            });

            System.out.println(collect);


        } catch (final IOException e) {
            e.printStackTrace();
        }

        return stockMap;
    }


    private Stock buildStock(final String[] input) {
        return new Stock(input[0]
                , LocalDate.parse(input[1], dateTimeFormatter)
                , TransactionType.valueOf(input[2])
                , Double.valueOf(input[3])
                , Double.valueOf(input[4])
                , input[5]
        );
    }


}
