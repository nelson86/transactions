package com.narabel.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Data
public class TransactionRequest {
    @NotNull(message = "Amount must not be NULL")
    private Double amount;

    @NotEmpty(message = "Type must not be Empty")
    private String type;

    private Long parentId;
}
