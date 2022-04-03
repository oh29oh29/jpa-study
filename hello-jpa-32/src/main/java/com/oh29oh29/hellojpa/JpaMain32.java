package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Member;
import com.oh29oh29.hellojpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain32 {

    public static void main(String[] args) {

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            final Team team1 = new Team();
            team1.setName("team1");
            em.persist(team1);

            final Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            member1.changeTeam(team1);
            em.persist(member1);

            em.flush();
            em.clear();

            final List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "member1")
                    .getResultList();

            for (Member member : resultList) {
                System.out.println("member = " + member);
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