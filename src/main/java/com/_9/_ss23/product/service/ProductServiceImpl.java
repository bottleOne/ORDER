package com._9._ss23.product.service;

import com._9._ss23.order.OrderException;
import com._9._ss23.product.domain.Product;
import com._9._ss23.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(Long productNum) {
        return productRepository.findById(productNum).orElse(Product.emptyProduct());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProducts(List<Long> productNums){
        return productRepository.selectProducts(productNums);
    }

    @Override
    public Product reduceProductCount(Product product, int itemQuantity) {
       return product.minusQuantity(itemQuantity);
    }

    @Override
    public void checkProduct(Product product, int orderProductCount) {
        if(product.isEmpty()){
            throw new OrderException("현재 없는 상품입니다.");
        }

        if(product.isProductAvailable(orderProductCount)){
            throw new OrderException("현재 상품의 수량이 없습니다.");
        }
    }
}
