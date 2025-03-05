package com._9._ss23.product.service;

import com._9._ss23.product.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProduct();

    Product getProduct(Long productNum) throws InterruptedException;
    List<Product> getProducts(List<Long> productNums);

    void checkProduct(Product product, int orderProductCount);

    void reduceProductCount(Long itemId, int itemQuantity) throws InterruptedException;
}
