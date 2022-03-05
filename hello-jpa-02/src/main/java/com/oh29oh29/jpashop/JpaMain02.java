package com.oh29oh29.jpashop;

import com.oh29oh29.jpashop.domain.Member;
import com.oh29oh29.jpashop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain02 {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpashop");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            case01(em);
            case02(em);

            tx.commit();
        } catch (Exception e) {
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

        final Member findMember = em.find(Member.class, member.getId());
        final List<Member> members = findMember.getTeam().getMembers();

        for (Member m : members) {
            System.out.println("m.getUsername() = " + m.getUsername());
        }
    }

    /**
     * 양방향에서 주의할 점
     * */
    private static void case02(EntityManager em) {
        final Team team = new Team();
        team.setName("team");
        em.persist(team);

        final Member member = new Member();
        member.setUsername("member");
        member.setTeam(team);
        em.persist(member);

        // 1차 캐시에서 조회
        final Member findMember = em.find(Member.class, member.getId());
        final List<Member> members = findMember.getTeam().getMembers();

        // 따라서 team에는 members가 존재하지 않아서 비어있다.
        System.out.println("members.size(): " + members.size());

        // 그래서 team에도 member를 추가해줘야 함.
        team.getMembers().add(member);

        // 양방향일 경우에는 양쪽에 값을 설정해야 한다.
    }
}
