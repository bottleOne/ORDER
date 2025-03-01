package com._9._ss23.order.process;


import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.order.dto.ProductOrderRequest;
import com._9._ss23.product.domain.Product;
import com._9._ss23.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class ProductOrderProcess extends AbstractProductOrderProcess<ProductOrderRequest> {

    private final ProductService productService;
    @Override
    protected List<OrderResponse> process(ProductOrderRequest...requests) {
        List<ProductOrderRequest> reqeustList = Arrays.stream(requests).toList();
        List<Long> productNums = reqeustList.stream()
                .map(ProductOrderRequest::getItemNumber)
                .collect(Collectors.toList());

        List<Product> products = productService.getProducts(productNums);

        List<ProductOrderRequest> checkedProducts = reqeustList.stream().map(r -> {
           return checkProduct(products, r);
        }).collect(Collectors.toList());

        return orderService.order(checkedProducts);
    }

    private ProductOrderRequest checkProduct(List<Product> products, ProductOrderRequest request){
        Product product = products.stream()
                .filter(p -> p.getId().equals(request.getItemNumber()))
                .findFirst()
                .orElse(Product.emptyProduct());

        productService.checkProduct(product, request.getOrderItemCnt());
        request.setProduct(product);

        return request;
    }
}
