package com._9._ss23.order.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class OrderResponse {
    protected String itemNm;
    protected Long orderId;
    protected Long itemId;
    protected int orderedItemCount;
}
