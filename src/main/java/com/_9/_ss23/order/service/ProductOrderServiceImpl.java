package com._9._ss23.order.service;

import com._9._ss23.order.OrderException;
import com._9._ss23.order.code.OrderState;
import com._9._ss23.order.domain.Order;
import com._9._ss23.order.domain.ProductOrder;
import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.order.dto.ProductOrderRequest;
import com._9._ss23.order.repoditory.OrderRepository;
import com._9._ss23.order.repoditory.ProductOrderRepository;
import com._9._ss23.product.domain.Product;
import com._9._ss23.product.domain.ProductOrderResponse;
import com._9._ss23.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductOrderServiceImpl implements ProductOrderService{

    private final OrderRepository orderRepository;
    private final ProductOrderRepository productOrderRepository;
    @Override
    public List<OrderResponse> order(List<ProductOrderRequest> productOrderRequests) {

        Order order = new Order(LocalDateTime.now());
        Order savedOrder = saveOrder(order);

        productOrderRequests.stream().forEach(po->po.setOrder(savedOrder));
        return saveOrderRecord(productOrderRequests);
    }

    @Override
    public List<Order> getOrders(List<Long> ids) {
        return orderRepository.findAllById(ids);
    }

    @Override
    public Order getOrder(Long orderId) {
        try {
            log.info("🔹 조회 요청 orderId: {}", orderId);
            return orderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderException("찾을수 없는 주문입니다"));
        }catch (ObjectOptimisticLockingFailureException e){
            log.error("주문조회 동시성충돌");
            return getOrder(orderId);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order saveOrder(Order order) {
        try {
            Order save = orderRepository.save(order);
            log.info("🔹 saveOrder() 실행 - orderId: {}", save.getId());
            return save;
        }catch (ObjectOptimisticLockingFailureException e){
            log.error("주문조회 동시성충돌");
            return saveOrder(order);
        }

    }

    @Transactional
    protected List<OrderResponse> saveOrderRecord(List<ProductOrderRequest> product) {
        Order order = getOrder(product.getFirst().getOrder().getId());
        List<ProductOrder> orderList = product.stream().map(p ->
             new ProductOrder(order, p.getProduct(), p.getOrderItemCnt(), OrderState.ORDER)
        ).collect(Collectors.toList());

        return productOrderRepository.saveAll(orderList).stream().map(po->
            ProductOrderResponse.newProductOrderResponse(po.getOrder().getId(), po.getProduct().getId(), po.getProduct().getProductName(), po.getItemCount())
        ).collect(Collectors.toList());
    }
}
