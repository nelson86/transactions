package com.narabel.transactions.service.v1;

import com.narabel.transactions.dto.SumOfTransactionResponse;

public interface GetSumOfTransactionsByIdService {

    SumOfTransactionResponse getSumForTransactions(Long transactionalId);
}
