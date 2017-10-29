package com.nutmeg.repository;

import com.nutmeg.model.Holding;
import com.nutmeg.model.Stock;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;


public class HoldingRepository {
    private static final Map<String, List<Holding>> holdings = new HashMap<>();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    public static final String CASH = "CASH";


    public static void resetRepositry() {
        holdings.clear();
    }

    public static Holding read(final Stock stock) {
        lock.readLock().lock();

        try {

            final Optional<Holding> result = holdings.entrySet().stream()
                    .filter(k -> k.getKey().equals(stock.getAccount()))
                    .flatMap(k -> k.getValue().stream())
                    .filter(k -> k.getAsset().equals(stock.getAsset()))
                    .findFirst();

            return result.isPresent() ? result.get() : new Holding(stock.getAsset(), 0);

        } finally {
            lock.readLock().unlock();
        }
    }

    public static void write(final Holding holding, final Stock stock) {
        lock.writeLock().lock();

        try {
            if (holding.getHolding() > 0) {

                final List<Holding> orDefault = holdings
                        .getOrDefault(stock.getAccount(), new ArrayList<>()).stream()
                        .filter(k -> !k.getAsset().equals(stock.getAsset()))
                        .collect(Collectors.toList());

                orDefault.add(holding);

                if (holdings.containsKey(stock.getAccount())) {
                    holdings.replace(stock.getAccount(), orDefault);
                } else {
                    holdings.put(stock.getAccount(), orDefault);
                }
            }

        } finally {
            lock.writeLock().unlock();
        }
    }


    public static Holding readCash(final String account) {
        lock.readLock().lock();

        try {

            final Optional<Holding> result = holdings.entrySet().stream()
                    .filter(k -> k.getKey().equals(account))
                    .flatMap(k -> k.getValue().stream())
                    .filter(k -> k.getAsset().equals(CASH))
                    .findFirst();

            return result.isPresent() ? result.get() : new Holding(CASH, 0);

        } finally {
            lock.readLock().unlock();
        }
    }


    public static void writeCash(final Holding holding, final String account) {
        lock.writeLock().lock();

        try {
            if (holding.getHolding() > 0) {

                final List<Holding> orDefault = holdings
                        .getOrDefault(account, new ArrayList<>()).stream()
                        .filter(k -> !k.getAsset().equals(CASH))
                        .collect(Collectors.toList());

                orDefault.add(holding);

                if (holdings.containsKey(account)) {
                    holdings.replace(account, orDefault);
                } else {
                    holdings.put(account, orDefault);
                }
            }

        } finally {
            lock.writeLock().unlock();
        }
    }


    public static Map<String, List<Holding>> readAll() {
        return holdings;
    }
}
