package com.narabel.transactions.service.v1.impl;

import com.narabel.transactions.dto.SumOfTransactionResponse;
import com.narabel.transactions.entity.Transaction;
import com.narabel.transactions.exception.TransactionNotFoundException;
import com.narabel.transactions.repository.TransactionJpaRepository;
import com.narabel.transactions.service.v1.FindTransactionByIdService;
import com.narabel.transactions.service.v1.GetSumOfTransactionsByIdService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
public class GetSumOfTransactionsByIdServiceImpl implements GetSumOfTransactionsByIdService {

    @Autowired
    private TransactionJpaRepository repository;

    @Autowired
    private FindTransactionByIdService findTransactionByIdService;

    @Override
    public SumOfTransactionResponse getSumForTransactions(Long transactionalId) {
        Transaction transaction = findTransactionByIdService.findById(transactionalId);
        if(Objects.isNull(transaction))
            throw new TransactionNotFoundException(transactionalId);

        Double accumulated = getAccumulatedOfAllTransactions(transaction);
        return new SumOfTransactionResponse(accumulated);
    }

    private Double getAccumulatedOfAllTransactions(Transaction transaction) {
        Double accumulated = transaction.getAmount();
        List<Transaction> transactionsTemp = Arrays.asList(transaction);

        while ( !transactionsTemp.isEmpty() ) {
            log.debug("idsTemp: {}", transactionsTemp.stream().map(Transaction::getId).collect(Collectors.toList()));
            transactionsTemp = repository.findByParentIn(transactionsTemp);
            accumulated += transactionsTemp.stream().mapToDouble(Transaction::getAmount).sum();
        }

        return accumulated;
    }

}
