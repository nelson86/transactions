package com.narabel.transactions.service.v1.impl;

import com.narabel.transactions.entity.Transaction;
import com.narabel.transactions.repository.TransactionJpaRepository;
import com.narabel.transactions.service.v1.GetTransactionByTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetTransactionByTypeServiceImpl implements GetTransactionByTypeService {

    @Autowired
    private TransactionJpaRepository repository;

    @Override
    public List<Long> findByType(String type) {
        List<Transaction> transactions = repository.findByType(type);
        if(transactions.isEmpty())
            return List.of();

        return transactions.stream().map(Transaction::getId).collect(Collectors.toList());
    }
}
