package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Member;

import javax.persistence.*;
import java.util.List;

public class JpaMain21 {

    public static void main(String[] args) {

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            final Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            final TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            final TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            final Query query3 = em.createQuery("select m.username, m.age from Member m");

            final List<Member> resultList = query1.getResultList();

            final Member singleResult = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

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
