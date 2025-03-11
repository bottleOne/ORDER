package com._9._ss23.order.vo;


public class OrderRequest {
    protected Long itemNumber;

    protected int orderItemCnt;

    public OrderRequest(Long itemNumber, int orderItemCnt){
        this.itemNumber = itemNumber;
        this.orderItemCnt = orderItemCnt;
    }


    public Long getItemNumber(){
        return this.itemNumber;
    }

    public int getOrderItemCnt(){
        return this.orderItemCnt;
    }
}
