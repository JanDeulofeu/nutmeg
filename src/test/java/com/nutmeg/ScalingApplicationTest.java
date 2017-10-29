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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ScalingApplicationTest {


    @Test
    public void validateTransactionsCalculationGivenDateBefore20170201() throws URISyntaxException, ExecutionException, InterruptedException {

        HoldingRepository.reset();


        final ExecutorService executor = Executors.newFixedThreadPool(2);

        final List<Future<Map<String, List<Holding>>>> futures = executor.invokeAll(Arrays.asList(new Worker(), new Worker()));

        final Map<String, List<Holding>> resultA = futures.get(0).get();
        final Map<String, List<Holding>> resultB = futures.get(1).get();

        assertThat(resultA).isEqualToComparingFieldByField(resultB);


//        final Holding accountA_VUSA = result.entrySet().stream()
//                .filter(k -> k.getKey().equals("NEAA0000"))
//                .flatMap(k -> k.getValue().stream())
//                .filter(s -> s.getAsset().equals("VUSA"))
//                .findFirst().get();
//
//        final Holding accountA_VUKE = result.entrySet().stream()
//                .filter(k -> k.getKey().equals("NEAA0000"))
//                .flatMap(k -> k.getValue().stream())
//                .filter(s -> s.getAsset().equals("VUKE"))
//                .findFirst().get();
//
//        final Holding accountA_GILS = result.entrySet().stream()
//                .filter(k -> k.getKey().equals("NEAA0000"))
//                .flatMap(k -> k.getValue().stream())
//                .filter(s -> s.getAsset().equals("GILS"))
//                .findFirst().get();
//
//        final Holding accountA_CASH = result.entrySet().stream()
//                .filter(k -> k.getKey().equals("NEAA0000"))
//                .flatMap(k -> k.getValue().stream())
//                .filter(s -> s.getAsset().equals("CASH"))
//                .findFirst().get();
//
//        final Holding accountB_CASH = result.entrySet().stream()
//                .filter(k -> k.getKey().equals("NEAB0001"))
//                .flatMap(k -> k.getValue().stream())
//                .filter(s -> s.getAsset().equals("CASH"))
//                .findFirst().get();
//
//        assertThat(new Holding("VUSA", 10)).
//
//                isEqualTo(accountA_VUSA);
//
//        assertThat(new Holding("VUKE", 20)).
//
//                isEqualTo(accountA_VUKE);
//
//        assertThat(new Holding("GILS", 10.5120)).
//
//                isEqualTo(accountA_GILS);
//
//        assertThat(new Holding("CASH", 17.6849)).
//
//                isEqualTo(accountA_CASH);
//
//        assertThat(new Holding("CASH", 10000)).
//
//                isEqualTo(accountB_CASH);

    }

    private class Worker implements Callable<Map<String, List<Holding>>> {

        @Override
        public Map<String, List<Holding>> call() throws Exception {

            final URI uri = new URI(getClass().getClassLoader().getResource("Transactions.csv").toString());

            final File file = new File(uri);

            return new HoldingCalculatorImpl().calculateHoldings(file, LocalDate.parse("20170201", DateTimeFormatter.ofPattern("yyyyMMdd")));
        }
    }
}
