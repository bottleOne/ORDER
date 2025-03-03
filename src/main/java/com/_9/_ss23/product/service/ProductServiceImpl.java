package com._9._ss23.product.service;

import com._9._ss23.order.OrderException;
import com._9._ss23.product.domain.Product;
import com._9._ss23.product.repository.ProductRepository;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional @Slf4j
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
    public List<Product> getProducts(List<Long> productNums){
        try{
            return productRepository.selectProducts(productNums);
        }catch (ObjectOptimisticLockingFailureException e){
            log.error("품목조회 동시성");
            return  getProducts(productNums);
        }

    }

    @Override
    @Transactional
    @Retryable(value = ObjectOptimisticLockingFailureException.class, maxAttempts = 5, backoff = @Backoff(delay = 100))
    public Product reduceProductCount(Long productId, int itemQuantity) {
        Product product = getProduct(productId);
        checkProduct(product, itemQuantity);
            Product save = productRepository.save(product.minusQuantity(itemQuantity));
            return save;
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
