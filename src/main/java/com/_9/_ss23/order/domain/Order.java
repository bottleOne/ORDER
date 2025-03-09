package com._9._ss23.order.domain;

import com._9._ss23.order.code.DeliveryJudgment;
import com._9._ss23.order.domain.ProductOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
@Table(name = "ORDERS")
public class Order {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id @Column(name = "order_id")
    private Long id;
    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrder> productOrders;
    private LocalDateTime orderDate;
    private LocalDateTime cancelDate;
    @Enumerated(EnumType.STRING)
    private DeliveryJudgment deliveryPay;
    @Column(name = "total_price", nullable = true)
    private int totalPrice;
    @Version
    private Long version;
    private Order(LocalDateTime orderDate){
        this.orderDate = orderDate;
        this.totalPrice = 0;
        this.deliveryPay = DeliveryJudgment.NONDELIVERYFEE;
    }

    public static Order createOrder(){
        return new Order(LocalDateTime.now());
    }

    public void addProductOrder(ProductOrder productOrder) {
        if(this.productOrders == null){
            this.productOrders = new ArrayList<>();
        }
        this.productOrders.add(productOrder);
    }

    public void setDeliveryPay(DeliveryJudgment deliveryPay){
        this.deliveryPay = deliveryPay;
    }
    public void setTotalPrice(int totalPrice){this.totalPrice = totalPrice;}
}
