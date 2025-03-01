package com._9._ss23.product.repository;

import com._9._ss23.product.domain.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> selectProducts(List<Long> productNum);
}
