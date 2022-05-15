package com.narabel.transactions.controller.v1;

import com.narabel.transactions.dto.TransactionRequest;
import com.narabel.transactions.entity.Transaction;
import com.narabel.transactions.exception.TransactionNotFoundException;
import com.narabel.transactions.service.v1.CreateTransactionService;
import com.narabel.transactions.service.v1.FindTransactionByIdService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Objects;

@Log4j2
@Validated
@RestController
@RequestMapping("/transactions")
public class CreateTransactionController {

    @Autowired
    private CreateTransactionService createTransactionService;

    @Autowired
    private FindTransactionByIdService findTransactionByIdService;

    @PutMapping("/{transactionId}")
    public ResponseEntity<?> createTransaction(
            @PathVariable Long transactionId,
            @Valid @RequestBody TransactionRequest transactionRequest
    ) {

        Transaction transactionParent = null;
        boolean existParentId = Objects.nonNull(transactionRequest.getParentId());
        if( existParentId )
            transactionParent = findTransactionByIdService.findById(transactionRequest.getParentId());

        if( existParentId && Objects.isNull(transactionParent) )
            throw new TransactionNotFoundException(
                    String.format("Not fount Transaction for ID: %s", transactionRequest.getParentId())
            );

        createTransactionService.createTransaction(
                Transaction.builder()
                        .id(transactionId)
                        .amount(transactionRequest.getAmount())
                        .type(transactionRequest.getType())
                        .parent(transactionParent)
                        .build()
        );

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
