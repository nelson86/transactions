package com.narabel.transactions.service.v1;

import com.narabel.transactions.entity.Transaction;

public interface FindTransactionByIdService {

    Transaction findById(Long transactionId);
}
