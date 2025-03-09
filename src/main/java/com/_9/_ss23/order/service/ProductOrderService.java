package com._9._ss23.order.service;

import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.order.dto.ProductOrderRequest;

import java.util.List;

public interface ProductOrderService extends OrderService<ProductOrderRequest>{
    int getTotalPrice(OrderResponse responses);
    int getDeliveryFee(OrderResponse responses);

}
