package com._9._ss23.order.process;

import com._9._ss23.order.dto.OrderResponse;
import com._9._ss23.order.dto.ProductReqeust;
import com._9._ss23.order.service.OrderService;
import com._9._ss23.product.domain.Product;
import com._9._ss23.product.domain.ProductOrderResponse;
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
public class ProductOrderProcess extends AbstractProductOrderProcess<ProductReqeust> {

    private final ProductService productService;
    @Override
    protected List<OrderResponse> process(ProductReqeust ...requests) {
        List<ProductReqeust> reqeustList = Arrays.stream(requests).toList();
        List<Long> productNums = reqeustList.stream()
                .map(ProductReqeust::getProductNumber)
                .collect(Collectors.toList());

        List<Product> products = productService.getProducts(productNums);

        return reqeustList.stream().map(r -> {
            Product product = products.stream()
                    .filter(p -> p.getId().equals(r.getProductNumber()))
                    .findFirst()
                    .orElse(Product.emptyProduct());

            productService.checkProduct(product, r.getProductQuantity());
            orderService.orderItem(product, r.getProductQuantity());

            return ProductOrderResponse.newProductOrderResponse(product.getProductName(), product.getProductQuantity());
        }).collect(Collectors.toList());
    }
}
