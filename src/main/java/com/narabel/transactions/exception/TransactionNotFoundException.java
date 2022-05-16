package com.narabel.transactions.exception;

public class TransactionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TransactionNotFoundException(Long transactionalId) {
        super(String.format("Not found Transaction for ID: %s", transactionalId));
    }
}
