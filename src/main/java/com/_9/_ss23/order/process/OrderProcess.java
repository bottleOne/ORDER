package com._9._ss23.order.process;

import com._9._ss23.order.code.OrderType;
import com._9._ss23.order.vo.OrderResponse;

import java.util.List;

public interface OrderProcess<T> {
    public List<OrderResponse> orderProcess(List<T> request);

    public boolean is(OrderType orderType);
}
