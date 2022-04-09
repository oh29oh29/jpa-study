package com.oh29oh29.service;

import com.oh29oh29.domain.Address;
import com.oh29oh29.domain.Member;
import com.oh29oh29.domain.Order;
import com.oh29oh29.domain.OrderStatus;
import com.oh29oh29.domain.item.Book;
import com.oh29oh29.exception.NotEnoughStockException;
import com.oh29oh29.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        final Member member = createMember();
        final Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        // when
        final Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        final Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        // given
        final Member member = createMember();
        final Book book = createBook("JPA", 10000, 10);

        int orderCount = 11;

        // when & then
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), orderCount));
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        final Member member = createMember();
        final Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        final Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        final Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 재고가 원복되어야 한다.", 10, book.getStockQuantity());
    }

    private Member createMember() {
        final Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        final Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}