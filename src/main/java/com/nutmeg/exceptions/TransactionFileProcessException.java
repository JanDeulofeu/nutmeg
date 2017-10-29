package com.nutmeg.exceptions;

public class TransactionFileProcessException extends RuntimeException {


    public TransactionFileProcessException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
