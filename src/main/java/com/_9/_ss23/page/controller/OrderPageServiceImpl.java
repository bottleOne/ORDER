package com._9._ss23.page.controller;

import com._9._ss23.order.domain.ProductOrder;
import com._9._ss23.order.dto.OrderRequest;
import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.order.dto.ProductOrderRequest;
import com._9._ss23.order.process.ProductOrderProcess;
import com._9._ss23.order.service.OrderService;
import com._9._ss23.order.service.ProductOrderService;
import com._9._ss23.page.dto.OrderDto;
import com._9._ss23.product.dto.ProductDto;
import com._9._ss23.product.service.ProductService;
import com._9._ss23.sale.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderPageServiceImpl implements OrderPageService {

    //TODO interface로
    private final ProductOrderService orderService;
    private final ProductService productService;
    private final SaleService saleService;
    private final ProductOrderProcess orderProcess;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProducts() {
         return productService.getAllProduct().stream().map(p -> ProductDto.to(p)).collect(Collectors.toList());
    }

    @Override
    public OrderDto order(List<ProductDto> productDtos) {
        List<ProductOrderRequest> requests = productDtos.stream().map(dto -> {
            return ProductOrderRequest.create(dto.getProductNumber(), dto.getProductQuantity());
        }).collect(Collectors.toList());
        List<OrderResponse> responses = orderProcess.orderProcess(requests);
        saleService.sale(responses);

        //TODO 분리해야함
        int totalPrice = orderService.getTotalPrice(responses.getFirst());
        int deliveryFee = orderService.getDeliveryFee(responses.getFirst());

       return OrderDto.create(totalPrice,deliveryFee,responses);
    }
}
