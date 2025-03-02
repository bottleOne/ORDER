package com._9._ss23.product.service;

import com._9._ss23.product.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProduct();

    Product getProduct(Long productNum);
    List<Product> getProducts(List<Long> productNums);

    void checkProduct(Product product, int orderProductCount);

    Product reduceProductCount(Product item, int itemQuantity);
}
