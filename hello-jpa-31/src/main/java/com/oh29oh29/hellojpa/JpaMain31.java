package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Member;
import com.oh29oh29.hellojpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain31 {

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

            final String query1 = "select m from Member m where m = :member";
            final List<Member> resultList = em.createQuery(query1, Member.class)
                    .setParameter("member", member1)
                    .getResultList();

            for (Member member : resultList) {
                System.out.println("member = " + member);
            }

            em.flush();
            em.clear();

            final String query2 = "select m from Member m where m.id = :memberId";
            final List<Member> resultList2 = em.createQuery(query2, Member.class)
                    .setParameter("memberId", member1.getId())
                    .getResultList();

            for (Member member : resultList2) {
                System.out.println("member = " + member);
            }

            em.flush();
            em.clear();

            final String query3 = "select m from Member m where m.team = :team";
            final List<Member> resultList3 = em.createQuery(query3, Member.class)
                    .setParameter("team", team1)
                    .getResultList();

            for (Member member : resultList3) {
                System.out.println("member = " + member);
            }

            em.flush();
            em.clear();

            final String query4 = "select m from Member m where m.team.id = :teamId";
            final List<Member> resultList4 = em.createQuery(query4, Member.class)
                    .setParameter("teamId", team1.getId())
                    .getResultList();

            for (Member member : resultList4) {
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