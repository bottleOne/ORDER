package com._9._ss23.order.service;

import com._9._ss23.order.dto.ProductReqeust;
import com._9._ss23.product.domain.Product;

import java.util.List;

public interface OrderService<T> {

    T orderItem(T item, int itemQuantity);
}
