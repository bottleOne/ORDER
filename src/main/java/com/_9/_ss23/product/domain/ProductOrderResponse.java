package com._9._ss23.product.domain;

import com._9._ss23.order.dto.OrderResponse;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderResponse extends OrderResponse {
    private String productNm;
    private int orderedProductCount;
    public static ProductOrderResponse newProductOrderResponse(String productNm, int orderedProductCount){
        return new ProductOrderResponse(productNm, orderedProductCount);
    }
}
