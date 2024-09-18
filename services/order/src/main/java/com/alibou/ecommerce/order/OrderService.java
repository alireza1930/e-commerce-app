package com.alibou.ecommerce.order;

import com.alibou.ecommerce.customer.CustomerClient;
import com.alibou.ecommerce.exception.BusinessException;
import com.alibou.ecommerce.kafka.OrderConfirmation;
import com.alibou.ecommerce.kafka.OrderProducer;
import com.alibou.ecommerce.orderline.OrderLineRequest;
import com.alibou.ecommerce.orderline.OrderLineService;
import com.alibou.ecommerce.product.ProductClient;
import com.alibou.ecommerce.product.PurchaseRequest;
import com.alibou.ecommerce.product.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest request) {
        // check the customer --> OpenFeign
        var customer = customerClient.findCustomerById(request.customerId()).orElseThrow(()
                -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        //purchase the products --> product-ms
        var purchasedProducts = productClient.purchaseProducts(request.products());
        var order = repository.save(mapper.toOrder(request));

        //persist order


        //persist order lines
        for (PurchaseRequest purchaseRequest :
                request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()));
        }



        //todo: start payment process


        //send the order confirmation -->notification-ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
        ));


        return order.getId();
    }
}
