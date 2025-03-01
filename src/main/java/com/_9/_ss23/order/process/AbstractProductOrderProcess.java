package com._9._ss23.order.process;

import com._9._ss23.order.OrderException;
import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.order.service.OrderService;
import com._9._ss23.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractProductOrderProcess<T> implements OrderProcessInterface<T>{

    protected  OrderService orderService;
    @Override
    public List<OrderResponse> orderProcess(T ...requests){
        try{
           return process(requests);
        }catch (OrderException e){
            log.error(e.getMessage());
            e.getMessage();
        }
        return new ArrayList<>();
    }

    protected abstract List<OrderResponse> process(T ...requests);
}
