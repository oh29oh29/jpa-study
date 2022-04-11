package com.oh29oh29.repository;

import com.oh29oh29.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o join o.member m", Order.class)
                .getResultList();
    }

    public List<Order> findAllByString(OrderSearch orderSearch) {
        /**
         * orderSearch 유무에 따라 분기를 통해 JPQL 완성할 수 있다
         * 권장하지 않는 방법
         * */

        return em.createQuery("select o from Order o join o.member m", Order.class)
                .getResultList();
    }
}