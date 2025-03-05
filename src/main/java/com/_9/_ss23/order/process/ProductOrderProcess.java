package com._9._ss23.order.process;


import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.order.dto.ProductOrderRequest;
import com._9._ss23.order.service.ProductOrderService;
import com._9._ss23.product.domain.Product;
import com._9._ss23.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class ProductOrderProcess extends AbstractProductOrderProcess<ProductOrderRequest> {

    private ProductService productService;

    @Autowired
    public ProductOrderProcess(ProductService productService, ProductOrderService productOrderService) {
        this.productService = productService;
        this.orderService = productOrderService;
    }

    @Override
    protected  List<OrderResponse> process(List<ProductOrderRequest> requests) {
        List<Long> productNums = requests.stream()
                .map(ProductOrderRequest::getItemNumber)
                .collect(Collectors.toList());

        List<Product> products = productService.getProducts(productNums);

        List<ProductOrderRequest> checkedProducts = requests.stream()
                .map(r -> checkProduct(products, r))
                .collect(Collectors.toList());

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
