package com.alibou.ecommerce.customer;

import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer firstName is required")
        String firstName,
        @NotNull(message = "Customer lastName is required")
        String lastName,
        @NotNull(message = "Customer Email is required")
        @NotNull(message = "Customer Email is not a valid address")
        String email,
        Address address
) {
}
