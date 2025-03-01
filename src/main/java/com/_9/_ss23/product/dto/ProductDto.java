package com._9._ss23.product.dto;

import com._9._ss23.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long productNumber;

    private String productName;

    private int productPrice;

    private int productQuantity;

    private int orderCount;

    public static ProductDto to(Product product){
        return ProductDto.builder()
                .productNumber(product.getId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productQuantity(product.getProductQuantity())
                .build();
    }

    public ProductDto setOrderCount(int orderCount){
        this.orderCount = orderCount;
        return this;
    }
}
