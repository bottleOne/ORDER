package com._9._ss23.order.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReqeust {

    private Long productNumber;

    private int productQuantity;
}
