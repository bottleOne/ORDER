package com._9._ss23.product.dto;

import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.product.domain.Product;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ProductDto {
    private Long productNumber;

    private String productName;

    private int productPrice;

    private int productQuantity;

    private int totalPrice;

    private int deliveryFee;

    private List<OrderResponse> responseList;
    public ProductDto(Long productNumber, int productQuantity){
        this.productNumber = productNumber;
        this.productQuantity = productQuantity;
    }
    public ProductDto(Long productNumber, String productName, int productPrice, int productQuantity) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public static ProductDto to(Product product){
        return  new ProductDto(product.getId(),
                product.getProductName(),
                product.getProductPrice(),
                product.getProductQuantity());
    }

}
