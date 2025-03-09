package com._9._ss23.page.dto;

import com._9._ss23.order.dto.OrderResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private int totalPrice;
    private int deliveryFee;
    private List<OrderResponse> responses;

    public OrderDto(int totalPrice, int deliveryFee, List<OrderResponse> responses) {
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.responses = responses;
    }

    public static OrderDto create(int totalPrice, int deliveryFee, List<OrderResponse> responses) {
        return new OrderDto(totalPrice, deliveryFee, responses);
    }
}
