package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Member;
import com.oh29oh29.hellojpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain16 {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            case01(em);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void case01(EntityManager em) {
        final Team team = new Team();
        team.setName("team");
        em.persist(team);

        final Member member = new Member();
        member.setUsername("member");
        member.setTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

        System.out.println("\n=== case 01 ===");
        System.out.println("[A] em.find(Member.class, member.getId())");
        final Member findMember = em.find(Member.class, member.getId());
        System.out.println("[B] findMember.getTeam().class: " + findMember.getTeam().getClass());
        String teamName = findMember.getTeam().getName();
        System.out.println("[C] findMember.getTeam().name: " + teamName);
        System.out.println();
    }
}
