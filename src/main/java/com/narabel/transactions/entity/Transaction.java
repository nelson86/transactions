package com.narabel.transactions.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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