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
@Table(name = "ORDERS")
public class Order {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id @Column(name = "order_id")
    private Long id;
    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrder> productOrders;
    private LocalDateTime orderDate;
    private LocalDateTime cancelDate;
    @Version
    private Long version;
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
