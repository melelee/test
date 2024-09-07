package org.springframework.test;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private Long id;
    private String username;
    private Long referrerId;
    private Integer level;
}




