package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Address;
import com.oh29oh29.hellojpa.domain.Member;
import com.oh29oh29.hellojpa.domain.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain19 {

    public static void main(String[] args) {

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            final Member member = new Member();
            member.setUsername("test");
            member.setWorkPeriod(new Period());
            member.setHomeAddress(new Address("a", "b", "c"));

            em.persist(member);

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
