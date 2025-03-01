package com._9._ss23.product.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity @Getter
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_number")
    private Long id;

    private String productName;

    private int productPrice;

    private int productQuantity;

    public Product (){
    }
    public Product minusQuantity(int stock){
        this.productQuantity = this.productQuantity - stock;
        return this;
    }
    public static Product emptyProduct(){
        return new Product();
    }

    public boolean isEmpty(){
        return this.id == null;
    }

    public boolean isProductAvailable(int productQuantity){
        return  this.productQuantity - productQuantity <= 0;
    }
}
