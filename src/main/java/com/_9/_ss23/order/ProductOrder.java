package com._9._ss23.order;

import com._9._ss23.order.code.OrderState;
import com._9._ss23.product.domain.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
public class ProductOrder {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id @Column(name = "product_order_id")
    private Long id;

    private int countItems;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
