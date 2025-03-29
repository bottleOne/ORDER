package com._9._ss23.order.repoditory;

import com._9._ss23.order.domain.Order;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    //@Lock(LockModeType.OPTIMISTIC)
    Order save(Order order);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Order> findById(Long id);
}
