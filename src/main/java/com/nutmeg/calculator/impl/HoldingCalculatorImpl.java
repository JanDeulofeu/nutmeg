package com.nutmeg.calculator.impl;

import com.nutmeg.calculator.HoldingCalculator;
import com.nutmeg.model.Holding;
import com.nutmeg.model.Stock;
import com.nutmeg.service.StockBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HoldingCalculatorImpl implements HoldingCalculator {


    @Override
    public Map<String, List<Holding>> calculateHoldings(final File transactionFile, final LocalDate date) {

        final Map<String, List<Holding>> stockMap = new HashMap<>();


        try (final Stream<String> lines = Files.lines(transactionFile.toPath())) {

            final List<Stock> collect = lines.map(line -> line.split(StockBuilder.SEPARATOR))
                    .map(StockBuilder::buildStock)
                    .collect(Collectors.toList());

            System.out.println(collect);


        } catch (final IOException e) {
            e.printStackTrace();
        }

        return stockMap;
    }


}
