package com._9._ss23.order;

import com._9._ss23.order.code.OrderState;
import com._9._ss23.product.domain.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter
public class Order {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id @Column(name = "order_id")
    private Long id;
    @OneToMany(mappedBy = "product_order_id", fetch = FetchType.LAZY)
    private List<ProductOrder> productOrders;
    private LocalDateTime orderDate;
    private LocalDateTime cancelDate;
}
