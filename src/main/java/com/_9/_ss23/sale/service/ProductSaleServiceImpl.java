package com._9._ss23.sale.service;

import com._9._ss23.product.domain.Product;
import com._9._ss23.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSaleServiceImpl implements SaleService<Product> {

    private final ProductService productService;
    @Override
    public Product sale(Product product, int quantity) {
        return productService.reduceProductCount(product, quantity);
    }
}
