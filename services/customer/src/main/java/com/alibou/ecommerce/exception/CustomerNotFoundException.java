package com.alibou.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerNotFoundException extends RuntimeException {

    private final String msg;

    public CustomerNotFoundException(String msg) {

        this.msg = msg;
    }
}
