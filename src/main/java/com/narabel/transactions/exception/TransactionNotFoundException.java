package com.narabel.transactions.exception;

public class TransactionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TransactionNotFoundException(String mensaje) {
        super(mensaje);
    }
}
