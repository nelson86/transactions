package com.narabel.transactions.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private Long id;

    private Double amount;

    private String type;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Transaction parent;
}