package com.narabel.transactions.service.v1.impl;

import com.narabel.transactions.entity.Transaction;
import com.narabel.transactions.repository.TransactionJpaRepository;
import com.narabel.transactions.service.v1.FindTransactionByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindTransactionByIdServiceImpl implements FindTransactionByIdService {

    @Autowired
    private TransactionJpaRepository repository;

    @Override
    public Transaction findById(Long transactionId) {
        return repository.findById(transactionId).orElse(null);
    }
}
