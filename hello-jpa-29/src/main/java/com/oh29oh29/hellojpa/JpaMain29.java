package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Member;
import com.oh29oh29.hellojpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class JpaMain29 {

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

            // 상태 필드: 경로 탐색의 끝 (탐색 X)
            final String query1 = "select m.username from Member m";
            final List<String> result1 = em.createQuery(query1, String.class).getResultList();

            for (String s : result1) {
                System.out.println("s = " + s);
            }

            // 단일 값 연관 경로: 묵시적 내부 조인 발생 (탐색 O)
            final String query2 = "select m.team from Member m";
            final List<Team> result2 = em.createQuery(query2, Team.class).getResultList();

            for (Team team1 : result2) {
                System.out.println("team1 = " + team1);
            }

            // 컬렉션 값 연관 경로: 묵시적 내부 조인 발생 (탐색 X)
            final String query3 = "select t.members from Team t";
            final Collection result3 = em.createQuery(query3, Collection.class).getResultList();

            for (Object o : result3) {
                System.out.println("o = " + o);
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
