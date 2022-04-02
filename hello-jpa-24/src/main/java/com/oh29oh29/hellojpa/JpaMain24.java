package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Member;
import com.oh29oh29.hellojpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain24 {

    public static void main(String[] args) {

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            final Team team = new Team();
            team.setName("test");
            em.persist(team);

            final Member member = new Member();
            member.setUsername("test");
            member.setAge(10);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // 내부 조
            final List<Member> innerJoin = em.createQuery("select m from Member m inner join m.team t", Member.class)
                    .getResultList();
            System.out.println(innerJoin);

            // 외부 조인
            final List<Member> leftOuterJoin = em.createQuery("select m from Member m left join m.team t", Member.class)
                    .getResultList();
            System.out.println(leftOuterJoin);

            // 세타 조인
            final List<Member> thetaJoin = em.createQuery("select m from Member m, Team t where m.username = t.name", Member.class)
                    .getResultList();
            System.out.println(thetaJoin);

            // 조인 대상 필터링
            final List<Member> filtering = em.createQuery("select m from Member m left join m.team t on t.name = 'test'", Member.class)
                    .getResultList();
            System.out.println(filtering);

            // 연관관계 없는 엔티티 외부 조인
            final List<Member> result = em.createQuery("select m from Member m left join Team t on m.username = t.name", Member.class)
                    .getResultList();
            System.out.println(result);

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
