package com.nutmeg.model;

import com.nutmeg.annotations.ThreadSafe;
import com.nutmeg.types.TransactionType;

import java.time.LocalDate;
import java.util.Objects;


@ThreadSafe
public class Stock {

    private final String account;
    private final LocalDate date;
    private final TransactionType transactionType;
    private final Double units;
    private final Double price;
    private final String asset;

    public Stock(final String account, final LocalDate date, final TransactionType transactionType, final Double units, final Double price, final String asset) {
        this.account = account;
        this.date = date;
        this.transactionType = transactionType;
        this.units = units;
        this.price = price;
        this.asset = asset;
    }

    public String getAccount() {
        return account;
    }

    public LocalDate getDate() {
        return date;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Double getUnits() {
        return units;
    }

    public Double getPrice() {
        return price;
    }

    public String getAsset() {
        return asset;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "account='" + account + '\'' +
                ", date=" + date +
                ", transactionType=" + transactionType +
                ", units=" + units +
                ", price=" + price +
                ", asset='" + asset + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Stock stock = (Stock) o;
        return Objects.equals(account, stock.account) &&
                Objects.equals(date, stock.date) &&
                transactionType == stock.transactionType &&
                Objects.equals(units, stock.units) &&
                Objects.equals(price, stock.price) &&
                Objects.equals(asset, stock.asset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, date, transactionType, units, price, asset);
    }
}
