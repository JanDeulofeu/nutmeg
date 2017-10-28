package com.nutmeg.service;

import com.nutmeg.model.Holding;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum HoldingManager {

    INSTANCE;

    private final ConcurrentMap<String, List<Holding>> holdings = new ConcurrentHashMap<>();

    HoldingManager() {
    }

    public void addHoldingByAccount(final String account, final List<Holding> holdings) {
        this.holdings.putIfAbsent(account, holdings);
    }

    public ConcurrentMap<String, List<Holding>> getHoldingd() {
        return holdings;
    }
}
