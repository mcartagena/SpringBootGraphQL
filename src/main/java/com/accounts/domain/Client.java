package com.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Client {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
}
