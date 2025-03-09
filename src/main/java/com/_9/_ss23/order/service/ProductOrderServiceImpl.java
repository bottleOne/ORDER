package com._9._ss23.order.service;

import com._9._ss23.order.OrderException;
import com._9._ss23.order.code.DeliveryJudgment;
import com._9._ss23.order.domain.Order;
import com._9._ss23.order.domain.ProductOrder;
import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.order.dto.ProductOrderRequest;
import com._9._ss23.order.repoditory.OrderRepository;
import com._9._ss23.order.repoditory.ProductOrderRepository;
import com._9._ss23.product.domain.ProductOrderResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
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
        Order savedOrder = saveOrder( Order.createOrder());

        productOrderRequests.stream().forEach(po->po.setOrder(savedOrder));

        List<OrderResponse> orderResponses = saveOrderRecord(productOrderRequests);
        checkDeliveryPay(savedOrder);

        return orderResponses;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
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
    public int getTotalPrice(OrderResponse responses) {
        Long orderId = responses.getOrderId();
        Order order = getOrder(orderId);
       return order.getTotalPrice();
    }

    @Override
    public int getDeliveryFee(OrderResponse responses) {
        Long orderId = responses.getOrderId();
        Order order = getOrder(orderId);
       return order.getDeliveryPay().getFee();
    }

    @Override
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

    protected List<OrderResponse> saveOrderRecord(List<ProductOrderRequest> product) {
        Order order = getOrder(product.getFirst().getOrder().getId());
        List<ProductOrder> orderList = product.stream().map(p ->
              ProductOrder.readyToOrder(order, p.getProduct(), p.getOrderItemCnt())
        ).collect(Collectors.toList());

        return productOrderRepository.saveAll(orderList).stream().map(po ->
                ProductOrderResponse.newProductOrderResponse(po.getOrder().getId(), po.getProduct().getId(), po.getProduct().getProductName(), po.getItemCount())
        ).collect(Collectors.toList());
    }

    private void checkDeliveryPay(Order order){
        int totalPrice = 0;
        List<ProductOrder> productOrders = order.getProductOrders();
       for(ProductOrder pOrder : productOrders){
           totalPrice += pOrder.getProduct().getProductPrice();
       }
        DeliveryJudgment deliveryPay = DeliveryJudgment.deliveryFee(DeliveryJudgment.BASICDELIVERYFEE, totalPrice);
        order.setTotalPrice(totalPrice);
        order.setDeliveryPay(deliveryPay);
    }
}
