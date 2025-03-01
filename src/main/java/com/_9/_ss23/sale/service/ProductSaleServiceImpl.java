package com._9._ss23.sale.service;

import com._9._ss23.order.domain.Order;
import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.order.repoditory.ProductOrderRepository;
import com._9._ss23.order.service.OrderService;
import com._9._ss23.product.domain.Product;
import com._9._ss23.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductSaleServiceImpl implements SaleService<Product> {

    private final ProductService productService;
    private final OrderService orderService;
    @Override
    public void sale(List<OrderResponse> orderResponse) {
        Order order = orderService.getOrder(orderResponse.getFirst().getOrderId());
        /**
         * TODO 결제로직
         */
    }
}
