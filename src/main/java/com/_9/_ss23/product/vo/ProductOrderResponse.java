package com._9._ss23.product.vo;

import com._9._ss23.order.vo.OrderResponse;
import lombok.*;

@Getter @Setter
public class ProductOrderResponse extends OrderResponse {

    public ProductOrderResponse(Long orderId, Long productId, String productNm, int orderedProductCount){
        this.orderId = orderId;
        this.itemId = productId;
        this.itemNm = productNm;
        this.orderedItemCount = orderedProductCount;
    }
    public static ProductOrderResponse newProductOrderResponse(Long oderId, Long productId,String productNm, int orderedProductCount){
        return new ProductOrderResponse(oderId, productId, productNm, orderedProductCount);
    }

}
