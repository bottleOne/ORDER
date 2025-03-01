package com._9._ss23.sale.service;

import com._9._ss23.product.domain.Product;

public interface SaleService<T> {

    public T sale(Product product, int quantity);
}
