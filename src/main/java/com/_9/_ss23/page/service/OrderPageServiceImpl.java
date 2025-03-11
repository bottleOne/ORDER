package com._9._ss23.page.service;

import com._9._ss23.order.code.OrderType;
import com._9._ss23.order.vo.OrderResponse;
import com._9._ss23.order.vo.ProductOrderRequest;
import com._9._ss23.order.process.OrderProcess;
import com._9._ss23.order.process.OrderProcessResolver;
import com._9._ss23.order.service.ProductOrderService;
import com._9._ss23.page.dto.OrderDto;
import com._9._ss23.product.vo.ProductDto;
import com._9._ss23.product.service.ProductService;
import com._9._ss23.sale.SaleResponse;
import com._9._ss23.sale.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderPageServiceImpl implements OrderPageService {

    //TODO interface로
    private final OrderProcessResolver orderProcessResolver;
    private final ProductService productService;
    private final SaleService saleService;



    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProducts() {
         return productService.getAllProduct().stream().map(p -> ProductDto.to(p)).collect(Collectors.toList());
    }

    @Override
    public OrderDto order(List<ProductDto> productDtos) {
        OrderProcess process = orderProcessResolver.getProcess(OrderType.BASIC);

        List<ProductOrderRequest> requests = productDtos.stream().map(dto ->
             ProductOrderRequest.create(dto.getProductNumber(), dto.getProductQuantity())
        ).collect(Collectors.toList());

        List<OrderResponse> responses = process.orderProcess(requests);

        SaleResponse saleResponse = saleService.sale(responses);

        return OrderDto.create(saleResponse.getTotalPrice(), saleResponse.getDeliveryFee(), responses);
    }



}
