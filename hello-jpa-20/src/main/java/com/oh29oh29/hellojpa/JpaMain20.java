package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Address;
import com.oh29oh29.hellojpa.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class JpaMain20 {

    public static void main(String[] args) {

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            final Member member = new Member();
            member.setUsername("test");
            member.setHomeAddress(new Address("a", "b", "c"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("보쌈");

            member.getAddressHistory().add(new Address("a1", "b1", "c1"));
            member.getAddressHistory().add(new Address("a2", "b2", "c2"));

            em.persist(member);

            em.flush();
            em.clear();


            System.out.println("\n=== case 01 ===");
            System.out.println("[A] em.find(Member.class, member.getId())");
            final Member findMember = em.find(Member.class, member.getId());
            System.out.println("[B] findMember.getAddressHistory()");
            final List<Address> addressHistory = findMember.getAddressHistory();
            System.out.println("[C] addressHistory: " + addressHistory);
            System.out.println("[D] findMember.getFavoriteFoods()");
            final Set<String> favoriteFoods = findMember.getFavoriteFoods();
            System.out.println("[E] favoriteFoods: " + favoriteFoods);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
