package com.nutmeg;

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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class ScalingApplicationTest {

    private static final DateTimeFormatter YYYY_M_MDD = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Test
    public void validateTransactionsCalculationGivenDateBefore20170201() throws URISyntaxException, ExecutionException, InterruptedException {

        HoldingRepository.reset();


        final ExecutorService executor = Executors.newFixedThreadPool(2);

        final File transactionsSplittedA = new File(new URI(getClass().getClassLoader().getResource("Transactions_A.csv").toString()));
        final File transactionsSplittedB = new File(new URI(getClass().getClassLoader().getResource("Transactions_B.csv").toString()));


        final Map<String, List<Holding>> resultA = executor.submit(() -> new HoldingCalculatorImpl().calculateHoldings(transactionsSplittedA, LocalDate.parse("20170201", YYYY_M_MDD))).get();
        final Map<String, List<Holding>> resultB = executor.submit(() -> new HoldingCalculatorImpl().calculateHoldings(transactionsSplittedB, LocalDate.parse("20170201", YYYY_M_MDD))).get();

        assertThat(resultA).isEqualToComparingFieldByField(resultB);


        final Holding accountA_VUSA = resultA.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAA0000"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("VUSA"))
                .findFirst().get();

        final Holding accountA_VUKE = resultA.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAA0000"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("VUKE"))
                .findFirst().get();

        final Holding accountA_GILS = resultA.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAA0000"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("GILS"))
                .findFirst().get();

        final Holding accountA_CASH = resultA.entrySet().stream()
                .filter(k -> k.getKey().equals("NEAA0000"))
                .flatMap(k -> k.getValue().stream())
                .filter(s -> s.getAsset().equals("CASH"))
                .findFirst().get();

        final Holding accountB_CASH = resultA.entrySet().stream()
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
}
