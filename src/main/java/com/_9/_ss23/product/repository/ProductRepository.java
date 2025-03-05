package com._9._ss23.product.repository;

import com._9._ss23.product.domain.Product;
import jakarta.persistence.LockModeType;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Lock(LockModeType.OPTIMISTIC)
    Optional<Product> findById(Long id);

    Product save(Product product);
}
