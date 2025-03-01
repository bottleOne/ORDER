package com._9._ss23.utill;

import com._9._ss23.product.domain.Product;

import java.util.List;

public class ProductTextLog extends ItemTextLog<Product> {
    @Override
    public String getTitle() {
        int spaceCount = 5;
        return  String.format("상품번호%" + spaceCount +
                "상품명%" + (spaceCount * 3) +
                "판매가격%" + spaceCount +
                "재고수");
    }

    @Override
    public String getContent(Product content) {
        int spaceCount = 5;
        return  String.format(content.getId() + spaceCount +
                content.getProductName() + spaceCount +
                content.getProductPrice() + spaceCount +
                content.getProductQuantity());
    }

}
