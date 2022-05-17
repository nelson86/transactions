package com.narabel.transactions.controller.v1;

import com.narabel.transactions.dto.SumOfTransactionResponse;
import com.narabel.transactions.service.v1.GetSumOfTransactionsByIdService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/transactions")
public class GetSumOfTransactionsByIdController {

    @Autowired
    private GetSumOfTransactionsByIdService service;

    @GetMapping("/sum/{transactionId}")
    public SumOfTransactionResponse getSumOfTransactions(
            @PathVariable Long transactionId
    ) {
        log.debug("Transaction Id: {}", transactionId);
        return service.getSumForTransactions(transactionId);
    }

}
