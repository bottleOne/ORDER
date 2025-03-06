package com._9._ss23.product.repository;

import com._9._ss23.product.domain.Product;
import jakarta.persistence.LockModeType;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findById(Long id);

    @Modifying //동시성 테스트시 product를 계속 물고 있어 직접 쿼리를 날려준다.test
    @Query("UPDATE Product p SET p.productQuantity = :quantity WHERE p.id = :productId")
    void update(@Param(value = "quantity") int quantity, @Param(value = "productId") Long productId);
}
