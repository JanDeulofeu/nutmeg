package com.nutmeg.model;

import java.io.Serializable;
import java.util.Objects;

public class Holding implements Serializable {

    private String asset;
    private double holding;

    public Holding() {
    }

    public Holding(final String asset, final double holding) {
        this.asset = asset;
        this.holding = holding;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(final String asset) {
        this.asset = asset;
    }

    public double getHolding() {
        return holding;
    }

    public void setHoldings(final double holding) {
        this.holding = holding;
    }

    public String toString() {
        return getAsset() + ":\t" + getHolding();
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Holding holding1 = (Holding) o;
        return Double.compare(holding1.holding, holding) == 0 &&
                Objects.equals(asset, holding1.asset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asset, holding);
    }
}