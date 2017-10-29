package com.nutmeg.calculator;


import com.nutmeg.calculator.impl.HoldingCalculatorImpl;
import com.nutmeg.model.Holding;
import com.nutmeg.repository.HoldingRepository;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HoldingCalculatorTest {

    private final HoldingCalculator holdingCalculator = new HoldingCalculatorImpl();


    @Test
    public void validateTransactionsCalculationGivenDateBefore20170201() throws URISyntaxException {

        HoldingRepository.reset();

        final URI uri = new URI(getClass().getClassLoader().getResource("Transactions.csv").toString());

        final File file = new File(uri);

        final Map<String, List<Holding>> result = holdingCalculator.calculateHoldings(file, LocalDate.parse("20170201", DateTimeFormatter.ofPattern("yyyyMMdd")));


        final Holding accountA_VUSA = result.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAA0000"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("VUSA"))
                .findFirst().get();

        final Holding accountA_VUKE = result.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAA0000"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("VUKE"))
                .findFirst().get();

        final Holding accountA_GILS = result.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAA0000"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("GILS"))
                .findFirst().get();

        final Holding accountA_CASH = result.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAA0000"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("CASH"))
                .findFirst().get();

        final Holding accountB_CASH = result.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAB0001"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("CASH"))
                .findFirst().get();

        assertThat(new Holding("VUSA", 10)).isEqualTo(accountA_VUSA);
        assertThat(new Holding("VUKE", 20)).isEqualTo(accountA_VUKE);
        assertThat(new Holding("GILS", 10.5120)).isEqualTo(accountA_GILS);
        assertThat(new Holding("CASH", 17.6849)).isEqualTo(accountA_CASH);
        assertThat(new Holding("CASH", 10000)).isEqualTo(accountB_CASH);

    }

    @Test
    public void validateTransactionsCalculationGivenDateAfter20170201() throws URISyntaxException {


        HoldingRepository.reset();

        final URI uri = new URI(getClass().getClassLoader().getResource("Transactions.csv").toString());

        final File file = new File(uri);

        final Map<String, List<Holding>> result = holdingCalculator.calculateHoldings(file, LocalDate.parse("20170301", DateTimeFormatter.ofPattern("yyyyMMdd")));


        final Holding accountA_VUSA = result.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAA0000"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("VUSA"))
                .findFirst().get();

        final Holding accountA_VUKE = result.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAA0000"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("VUKE"))
                .findFirst().get();

        final Holding accountA_GILS = result.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAA0000"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("GILS"))
                .findFirst().get();

        final Holding accountA_CASH = result.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAA0000"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("CASH"))
                .findFirst().get();

        final Holding accountB_CASH = result.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAB0001"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("CASH"))
                .findFirst().get();

        assertThat(new Holding("VUSA", 10)).isEqualTo(accountA_VUSA);
        assertThat(new Holding("VUKE", 20)).isEqualTo(accountA_VUKE);
        assertThat(new Holding("GILS", 10.5120)).isEqualTo(accountA_GILS);
        assertThat(new Holding("CASH", 17.6849)).isEqualTo(accountA_CASH);
        assertThat(new Holding("CASH", 5000)).isEqualTo(accountB_CASH);

    }


}