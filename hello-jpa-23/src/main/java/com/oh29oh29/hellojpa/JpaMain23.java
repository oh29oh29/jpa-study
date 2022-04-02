package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain23 {

    public static void main(String[] args) {

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            for (int i = 0; i < 50; i++) {
                final Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            final List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(10)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : result) {
                System.out.println(member);
            }

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
