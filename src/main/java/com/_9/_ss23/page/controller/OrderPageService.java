package com._9._ss23.page.controller;

import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.page.dto.OrderDto;
import com._9._ss23.product.dto.ProductDto;

import java.util.List;

public interface OrderPageService {

    public List<ProductDto> getProducts();

    public OrderDto order(List<ProductDto> productDtos);
}
