package org.springframework.test;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Commission {
    private Long commissionId;
    private Long transactionId;
    private Long userId;
    private Double amount;
}
