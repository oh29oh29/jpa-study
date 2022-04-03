package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Member;
import com.oh29oh29.hellojpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain33 {

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

            // FLUSH 자동 호출됨

            final String query1 = "update Member m set m.age = 20";
            final int resultCount = em.createQuery(query1).executeUpdate();

            System.out.println("resultCount = " + resultCount);

            // 실제 DB에는 데이터 변경이 되었지만 영속성 컨텍스트에는 수정된 값으로 반영되지 않은 상태
            System.out.println("member1 age = " + member1.getAge());
            System.out.println("member2 age = " + member2.getAge());
            System.out.println("member3 age = " + member3.getAge());

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