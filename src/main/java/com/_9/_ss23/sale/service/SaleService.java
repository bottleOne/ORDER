package com._9._ss23.sale.service;

import com._9._ss23.order.vo.OrderResponse;
import com._9._ss23.sale.SaleResponse;

import java.util.List;

public interface SaleService<T> {

    public SaleResponse sale(List<OrderResponse> orderResponse);
}
