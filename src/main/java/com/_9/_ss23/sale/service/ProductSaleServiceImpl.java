package com._9._ss23.sale.service;

import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.product.domain.Product;
import com._9._ss23.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductSaleServiceImpl implements SaleService<Product> {

    private final ProductService productService;
    @Override
    public  void sale(List<OrderResponse> orderResponse) {
        List<Long> productIds = orderResponse.stream()
                .map(OrderResponse::getItemId)
                .collect(Collectors.toList());

        List<Product> products = productService.getProducts(productIds);

        orderResponse.stream().forEach(r -> {
            productService.reduceProductCount(
                    products.stream()
                            .filter(p -> p.getId().equals(r.getItemId()))
                            .findFirst().get()
                            .getId()
                    , r.getOrderedItemCount()
            );
        });
    }
}
