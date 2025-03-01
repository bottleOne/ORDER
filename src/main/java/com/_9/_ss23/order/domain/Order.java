package com._9._ss23.order.domain;

import com._9._ss23.order.domain.ProductOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
public class Order {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id @Column(name = "order_id")
    private Long id;
    @OneToMany(mappedBy = "product_order_id", fetch = FetchType.LAZY)
    private List<ProductOrder> productOrders;
    private LocalDateTime orderDate;
    private LocalDateTime cancelDate;

    public Order(LocalDateTime orderDate){
        this.orderDate = orderDate;
    }

    public void addProductOrder(ProductOrder productOrder) {
        if(this.productOrders == null){
            this.productOrders = new ArrayList<>();
        }
        this.productOrders.add(productOrder);
    }
}
