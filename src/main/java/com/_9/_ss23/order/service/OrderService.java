package com._9._ss23.order.service;

import com._9._ss23.order.domain.Order;
import com._9._ss23.order.vo.OrderResponse;

import java.util.List;

public interface OrderService<T> {

    List<OrderResponse> order(List<T> item);
    List<Order> getOrders();
    Order getOrder(Long orderId);
    Order saveOrder(Order order);

}
