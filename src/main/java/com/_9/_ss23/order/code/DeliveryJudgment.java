package com._9._ss23.order.code;

import lombok.Getter;

@Getter
public enum DeliveryJudgment {
    BASICDELIVERYFEE(50000, 2500),
    NONDELIVERYFEE(0,0);

    private int standard;
    private int fee;

    DeliveryJudgment(int standard, int i) {
        this.standard = standard;
    }

    public static DeliveryJudgment deliveryFee(DeliveryJudgment deliveryJudgment ,int totalPrice){
        if(deliveryJudgment.getStandard() < totalPrice) return deliveryJudgment;
        else return DeliveryJudgment.NONDELIVERYFEE;
    }
}
