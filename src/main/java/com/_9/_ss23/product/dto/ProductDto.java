package com._9._ss23.product.dto;

import com._9._ss23.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDto {
    private Long productNumber;

    private String productName;

    private int productPrice;

    private int productQuantity;

    private int orderCount;

    public ProductDto(Long productNumber, String productName, int productPrice, int productQuantity) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.orderCount = 0;
    }

    public static ProductDto to(Product product){
        return  new ProductDto(product.getId(),
                product.getProductName(),
                product.getProductPrice(),
                product.getProductQuantity());
    }

    public ProductDto setOrderCount(int orderCount){
        this.orderCount = orderCount;
        return this;
    }
}
