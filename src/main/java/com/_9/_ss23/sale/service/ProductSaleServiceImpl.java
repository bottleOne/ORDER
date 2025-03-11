package com._9._ss23.sale.service;

import com._9._ss23.order.domain.Order;
import com._9._ss23.order.service.OrderService;
import com._9._ss23.order.service.ProductOrderService;
import com._9._ss23.order.vo.OrderResponse;
import com._9._ss23.product.domain.Product;
import com._9._ss23.product.service.ProductService;
import com._9._ss23.sale.SaleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductSaleServiceImpl implements SaleService<Product> {

    private final ProductService productService;

    private final ProductOrderService orderService;
    @Override
    public SaleResponse sale(List<OrderResponse> orderResponse) {
        List<Long> productIds = orderResponse.stream()
                .map(OrderResponse::getItemId)
                .collect(Collectors.toList());

        List<Product> products = productService.getProducts(productIds);

        orderResponse.stream().forEach(r -> {
            try {
                productService.reduceProductCount(
                        products.stream()
                                .filter(p -> p.getId().equals(r.getItemId()))
                                .findFirst().get()
                                .getId()
                        , r.getOrderedItemCount()
                );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        int totalPrice = getTotalPrice(orderResponse.getFirst());
        int deliveryFee = getDeliveryFee(orderResponse.getFirst());

        return SaleResponse.create(totalPrice, deliveryFee, orderResponse);
    }


    private int getTotalPrice(OrderResponse responses) {
        Long orderId = responses.getOrderId();
        Order order = orderService.getOrder(orderId);
        return order.getTotalPrice();
    }

    private int getDeliveryFee(OrderResponse responses) {
        Long orderId = responses.getOrderId();
        Order order = orderService.getOrder(orderId);
        return order.getDeliveryPay().getFee();
    }
}
