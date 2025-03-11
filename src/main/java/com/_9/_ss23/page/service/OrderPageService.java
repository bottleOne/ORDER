package com._9._ss23.page.service;

import com._9._ss23.page.dto.OrderDto;
import com._9._ss23.product.vo.ProductDto;

import java.util.List;

public interface OrderPageService {

    public List<ProductDto> getProducts();

    public OrderDto order(List<ProductDto> productDtos);
}
