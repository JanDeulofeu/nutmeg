package com.nutmeg.repository;

import com.nutmeg.annotations.ThreadSafe;
import com.nutmeg.model.Holding;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;


@ThreadSafe
public class HoldingRepository {
    private static final Map<String, List<Holding>> holdings = new HashMap<>();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    public static final String CASH = "CASH";


    public static Holding read(final String account, final String asset) {
        lock.readLock().lock();

        try {

            final Optional<Holding> result = holdings.entrySet().stream()
                    .filter(k -> k.getKey().equals(account))
                    .flatMap(k -> k.getValue().stream())
                    .filter(k -> k.getAsset().equals(asset))
                    .findFirst();

            return result.isPresent() ? result.get() : new Holding(asset, 0);

        } finally {
            lock.readLock().unlock();
        }
    }


    public static void write(final Holding holding, final String account, final String asset) {
        lock.writeLock().lock();

        try {
            if (holding.getHolding() > 0) {

                final List<Holding> orDefault = holdings
                        .getOrDefault(account, new ArrayList<>()).stream()
                        .filter(k -> !k.getAsset().equals(asset))
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

    public static void reset() {
        holdings.clear();
    }
}
