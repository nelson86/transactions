package com.narabel.transactions.service.v1.impl;

import com.narabel.transactions.entity.Transaction;
import com.narabel.transactions.repository.TransactionJpaRepository;
import com.narabel.transactions.service.v1.CreateTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateTransactionServiceImpl implements CreateTransactionService {

    @Autowired
    private TransactionJpaRepository repository;

    @Override
    public void createTransaction(Transaction transaction) {
        repository.save(transaction);
    }

}
