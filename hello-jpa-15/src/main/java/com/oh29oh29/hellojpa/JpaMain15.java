package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain15 {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            case01(em);
            case02(em);
            case03(em);
            case04(em);
            case05(em);
            case06(em);

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
        final Member member = new Member();
        member.setUsername("member");
        em.persist(member);

        em.flush();
        em.clear();

        System.out.println("\n=== case 01 ===");
        System.out.println("[A] em.getReference(Member.class, member.getId())");
        final Member referenceMember = em.getReference(Member.class, member.getId());
        System.out.println("[B] referenceMember.class: " + referenceMember.getClass());
        System.out.println();
    }

    private static void case02(EntityManager em) {
        final Member member = new Member();
        member.setUsername("member");
        em.persist(member);

        em.flush();
        em.clear();

        System.out.println("\n=== case 02 ===");
        System.out.println("[A] em.getReference(Member.class, member.getId())");
        final Member referenceMember = em.getReference(Member.class, member.getId());
        System.out.println("[B] referenceMember.getUsername()");
        final String username = referenceMember.getUsername();
        System.out.println("[C] referenceMember.username: " + username);
        System.out.println();
    }

    private static void case03(EntityManager em) {
        final Member member1 = new Member();
        member1.setUsername("member1");
        em.persist(member1);

        final Member member2 = new Member();
        member2.setUsername("member2");
        em.persist(member2);

        em.flush();
        em.clear();

        System.out.println("\n=== case 03 ===");
        System.out.println("[A] em.find(Member.class, member1.getId())");
        final Member findMember = em.find(Member.class, member1.getId());
        System.out.println("[B] em.getReference(Member.class, member2.getId())");
        final Member referenceMember = em.getReference(Member.class, member2.getId());
        System.out.println("[C] findMember.class == referenceMember.class: " + (findMember == referenceMember));
        System.out.println("[D] findMember instanceof Member: " + (findMember instanceof Member));
        System.out.println("[E] referenceMember instanceof Member: " + (referenceMember instanceof Member));
        System.out.println();
    }

    private static void case04(EntityManager em) {
        final Member member = new Member();
        member.setUsername("member");
        em.persist(member);

        em.flush();
        em.clear();

        System.out.println("\n=== case 04 ===");
        System.out.println("[A] em.find(Member.class, member.getId())");
        final Member findMember = em.find(Member.class, member.getId());
        System.out.println("[B] em.getReference(Member.class, member.getId())");
        final Member referenceMember = em.getReference(Member.class, member.getId());
        System.out.println("[C] findMember == referenceMember: " + (findMember == referenceMember));
        System.out.println("[D] findMember.class: " + findMember.getClass());
        System.out.println("[E] referenceMember.class: " + referenceMember.getClass());
        System.out.println();
    }

    private static void case05(EntityManager em) {
        final Member member = new Member();
        member.setUsername("member");
        em.persist(member);

        em.flush();
        em.clear();

        System.out.println("\n=== case 05 ===");
        System.out.println("[A] em.getReference(Member.class, member.getId())");
        final Member referenceMember = em.getReference(Member.class, member.getId());
        System.out.println("[B] em.find(Member.class, member.getId())");
        final Member findMember = em.find(Member.class, member.getId());
        System.out.println("[C] referenceMember == findMember: " + (findMember == referenceMember));
        System.out.println("[D] referenceMember.class: " + referenceMember.getClass());
        System.out.println("[E] findMember.class: " + findMember.getClass());
        System.out.println();
    }

    private static void case06(EntityManager em) {
        try {
            final Member member = new Member();
            member.setUsername("member");
            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("\n=== case 06 ===");
            System.out.println("[A] em.getReference(Member.class, member.getId())");
            final Member referenceMember = em.getReference(Member.class, member.getId());
            System.out.println("[B] referenceMember.class: " + referenceMember.getClass());
            System.out.println("[C] em.detach(referenceMember);");
            em.detach(referenceMember);
            System.out.println("[D] referenceMember.getUsername()");
            referenceMember.getUsername();
        } catch (Exception e) {
            System.out.println("[E] exception: " + e.getMessage());
            System.out.println();
        }
    }
}
