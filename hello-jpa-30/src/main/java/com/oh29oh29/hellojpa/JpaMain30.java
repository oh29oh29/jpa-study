package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Member;
import com.oh29oh29.hellojpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain30 {

    public static void main(String[] args) {

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            final Team team1 = new Team();
            team1.setName("team1");
            em.persist(team1);

            final Team team2 = new Team();
            team2.setName("team2");
            em.persist(team2);

            final Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            member1.changeTeam(team1);
            em.persist(member1);

            final Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(10);
            member2.changeTeam(team1);
            em.persist(member2);

            final Member member3 = new Member();
            member3.setUsername("member3");
            member3.setAge(10);
            member3.changeTeam(team2);
            em.persist(member3);

            em.flush();
            em.clear();

            final String query1 = "select m from Member m";
            final List<Member> resultList = em.createQuery(query1, Member.class).getResultList();

            for (Member member : resultList) {
                System.out.println("member = " + member + ", team = " + member.getTeam());
            }

            em.flush();
            em.clear();

            final String query2 = "select m from Member m join fetch m.team";
            final List<Member> resultList2 = em.createQuery(query2, Member.class).getResultList();

            for (Member member : resultList2) {
                System.out.println("member = " + member + ", team = " + member.getTeam());
            }

            em.flush();
            em.clear();

            final String query3 = "select t from Team t join fetch t.members";
            final List<Team> resultList3 = em.createQuery(query3, Team.class).getResultList();

            for (Team team : resultList3) {
                System.out.println("team = " + team + ", members = " + team.getMembers());
            }

            em.flush();
            em.clear();

            final String query4 = "select distinct t from Team t join fetch t.members";
            final List<Team> resultList4 = em.createQuery(query4, Team.class).getResultList();

            // jpql문에 distinct가 추가되었지만 쿼리 결과에서 중복이 제거된것은 아니다.
            // jpql에서 distinct를 추가하면 애플리케이션 상에서도 중복 제거 작업을 수행한다.
            for (Team team : resultList4) {
                System.out.println("team = " + team + ", members = " + team.getMembers());
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