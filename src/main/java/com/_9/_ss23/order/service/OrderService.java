package com._9._ss23.order.service;

import com._9._ss23.order.domain.Order;
import com._9._ss23.order.dto.OrderResponse;

import java.util.List;

public interface OrderService<T> {

    List<OrderResponse> order(List<T> item);
    List<Order> getOrders(List<Long> ids);
    Order getOrder(Long orderId);
    Order saveOrder(Order order);

}
