package com._9._ss23.order.domain;

import com._9._ss23.order.code.DeliveryJudgment;
import com._9._ss23.order.code.OrderState;
import com._9._ss23.product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor
public class ProductOrder {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id @Column(name = "product_order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryJudgment deliveryPay;

    private int itemCount;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Version
    private Long version;
    private ProductOrder(Order order, Product product, int itemCount, OrderState orderState){
        this.order = order;
        this.product = product;
        this.itemCount = itemCount;
        this.orderState = orderState;
        order.addProductOrder(this);
    }

    public static ProductOrder readyToOrder(Order order, Product product, int itemCount){
        return new ProductOrder(order, product, itemCount, OrderState.ORDER);
    }
}
