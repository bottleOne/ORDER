package com._9._ss23.sale.service;

import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.product.domain.Product;

import java.util.List;

public interface SaleService<T> {

    public void sale(List<OrderResponse> orderResponse);
}
