package com._9._ss23.product.repository;


import com._9._ss23.product.domain.Product;
import com._9._ss23.product.domain.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;


import java.util.List;

import static com._9._ss23.product.domain.QProduct.product;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public List<Product> selectProducts(List<Long> productNum) {

       return queryFactory.selectFrom(product)
                .where(product.id.in(productNum))
                .fetch();
    }
}
