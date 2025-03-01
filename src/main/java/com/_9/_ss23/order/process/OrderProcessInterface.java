package com._9._ss23.order.process;

import com._9._ss23.order.dto.OrderResponse;

import java.util.List;

public interface OrderProcessInterface<T> {
    public List<OrderResponse> orderProcess(T ...request);

}
