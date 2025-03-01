package com._9._ss23.order.service;

import com._9._ss23.product.domain.Product;
import com._9._ss23.product.service.ProductService;
import com._9._ss23.sale.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductOrderServiceImpl implements OrderService<Product>{

    private final ProductService productService;
    @Override
    public Product orderItem(Product product, int itemQuantity) {
        return  productService.reduceProductCount(product, itemQuantity);
    }
}
