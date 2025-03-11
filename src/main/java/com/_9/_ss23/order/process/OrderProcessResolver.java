package com._9._ss23.order.process;

import com._9._ss23.order.OrderException;
import com._9._ss23.order.code.OrderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderProcessResolver {

    private final List<OrderProcess> orderProcessInterfaceList;

    public OrderProcess getProcess(OrderType orderType){
      return   orderProcessInterfaceList.stream()
                .filter(orderProcess -> orderProcess.is(orderType))
                .findFirst()
                .orElseThrow(()-> new OrderException("UNKNOWN TYPE"));
    }

}
