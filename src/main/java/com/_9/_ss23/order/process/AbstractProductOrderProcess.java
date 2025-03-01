package com._9._ss23.order.process;

import com._9._ss23.order.OrderException;
import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.order.service.OrderService;
import com._9._ss23.sale.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public abstract class AbstractProductOrderProcess<T> implements OrderProcessInterface<T>{

    protected  OrderService orderService;
    protected SaleService saleService;
    @Override
    public List<OrderResponse> orderProcess(T ...requests){
        try{
            List<OrderResponse> process = process(requests);
            saleService.sale(process);
            return process(requests);
        }catch (OrderException e){
            log.error(e.getMessage());
            e.getMessage();
        }
        return new ArrayList<>();
    }

    protected abstract List<OrderResponse> process(T ...requests);
}
