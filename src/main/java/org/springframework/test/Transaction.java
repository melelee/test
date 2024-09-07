package org.springframework.test;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
    private Long transactionId;
    private Long userId;
    private Double amount;
}