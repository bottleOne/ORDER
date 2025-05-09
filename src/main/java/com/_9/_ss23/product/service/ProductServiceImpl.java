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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
   @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Product getProduct(Long productNum)  {
            return productRepository.findById(productNum).get();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Product> getProducts(List<Long> productNums){
        try{
            return productRepository.selectProducts(productNums);
        }catch (ObjectOptimisticLockingFailureException e){
            log.error("품목조회 동시성");
            return getProducts(productNums);
        }
    }

    @Override
    public void reduceProductCount(Long productId, int itemQuantity) throws InterruptedException {
        Product product = getProduct(productId);
        try{
            checkProduct(product, itemQuantity);
            saveProduct(product.minusQuantity(itemQuantity));
        }catch (ObjectOptimisticLockingFailureException e){
            log.error("동시성 이슈");
        }
    }

    public void saveProduct(Product product){
        try {
            productRepository.save(product);
        }catch (ObjectOptimisticLockingFailureException e){
            log.info("품목저장 동시성");
        }
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
