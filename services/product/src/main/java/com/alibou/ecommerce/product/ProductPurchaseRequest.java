package com.alibou.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull(message = "Product is mandatory")
        Integer productId,

        @NotNull(message = "Quantity is mandatory")
        @Positive(message = "Quantity should be positive")
        double quantity
) {
}
