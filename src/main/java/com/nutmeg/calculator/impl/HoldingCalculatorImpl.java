package com.nutmeg.calculator.impl;

import com.nutmeg.annotations.ThreadSafe;
import com.nutmeg.calculator.HoldingCalculator;
import com.nutmeg.model.Holding;
import com.nutmeg.model.Stock;
import com.nutmeg.repository.HoldingRepository;
import com.nutmeg.service.StockBuilder;
import com.nutmeg.service.StockConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ThreadSafe
public class HoldingCalculatorImpl implements HoldingCalculator {

    private final StockConverter stockConverter = new StockConverter();

    @Override
    public Map<String, List<Holding>> calculateHoldings(final File transactionFile, final LocalDate date) {


        System.out.println(Thread.currentThread().getId());
        try (final Stream<String> lines = Files.lines(transactionFile.toPath())) {

            final List<Stock> transactions = lines.map(line -> line.split(StockBuilder.SEPARATOR))
                    .map(StockBuilder::buildStock)
                    .filter(t -> !t.getDate().isAfter(date))
                    .collect(Collectors.toList());

            stockConverter.convert(transactions);


        } catch (final IOException e) {
            e.printStackTrace();
        }

        return HoldingRepository.readAll();
    }
}
