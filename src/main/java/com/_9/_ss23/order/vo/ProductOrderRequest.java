package com._9._ss23.order.vo;

import com._9._ss23.order.domain.Order;
import com._9._ss23.product.domain.Product;
import lombok.*;

@Getter @Setter
public class ProductOrderRequest extends OrderRequest{

   private Product product;
   private Order order;

    public ProductOrderRequest(Long itemNumber, int orderItemCnt) {
        super(itemNumber, orderItemCnt);
    }

    public static ProductOrderRequest create(Long itemNumber, int orderItemCnt){
        return new ProductOrderRequest(itemNumber, orderItemCnt);
    }
    @Override
    public Long getItemNumber() {
        return super.getItemNumber();
    }

    @Override
    public int getOrderItemCnt() {
        return super.getOrderItemCnt();
    }
}
