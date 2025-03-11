package com._9._ss23.sale;

import com._9._ss23.order.vo.OrderResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SaleResponse {
    private int totalPrice;
    private int deliveryFee;
    private List<OrderResponse> responseList;

    private SaleResponse(int totalPrice, int deliveryFee, List<OrderResponse> responseList) {
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.responseList = responseList;
    }

    public static SaleResponse create(int totalPrice, int deliveryFee, List<OrderResponse> responseList){
        return new SaleResponse(totalPrice, deliveryFee, responseList);
    }
}
