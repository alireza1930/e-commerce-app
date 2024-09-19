package com.alibou.ecommerce.order;

import com.alibou.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        Integer id,
        String reference,

        BigDecimal amount,

        PaymentMethod paymentMethod,

        String customerId

) {
}
