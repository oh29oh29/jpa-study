package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Address;
import com.oh29oh29.hellojpa.domain.Member;
import com.oh29oh29.hellojpa.domain.Team;
import com.oh29oh29.hellojpa.dto.MemberDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain22 {

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

            // 엔티티 프로젝션
            final List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
            // 엔티티 프로젝션 (조인)
            final List<Team> result2 = em.createQuery("select m.team from Member m", Team.class).getResultList();
            // 임베디드 타입 프로젝션
            final List<Address> result3 = em.createQuery("select o.address from Order o", Address.class).getResultList();
            // 스칼라 타입 프로젝션
            final List result4 = em.createQuery("select m.username, m.age from Member m").getResultList();
            // distinct
            final List result5 = em.createQuery("select distinct m.username, m.age from Member m").getResultList();

            final Object o = result5.get(0);
            final Object[] oResult = (Object[]) o;
            System.out.println("username = " + oResult[0]);
            System.out.println("age = " + oResult[1]);

            final List<MemberDTO> result6 = em.createQuery("select new com.oh29oh29.hellojpa.dto.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();
            final MemberDTO memberDTO = result6.get(0);
            System.out.println("memberDTO username = " + memberDTO.getUsername());
            System.out.println("memberDTO age = " + memberDTO.getAge());

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
