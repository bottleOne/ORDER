package com._9._ss23.product.repository;

import com._9._ss23.product.domain.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface ProductRepositoryCustom {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Product> selectProducts(List<Long> productNum);
}
