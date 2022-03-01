package com.oh29oh29.hellojpa;

import com.oh29oh29.hellojpa.domain.Item;
import com.oh29oh29.hellojpa.domain.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain13 {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            final Movie movie = new Movie();
            movie.setDirector("director");
            movie.setActor("actor");
            movie.setName("movieName");
            movie.setPrice(10000);
            em.persist(movie);

            em.flush();
            em.clear();

            final Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie: " + findMovie);

            em.clear();

            final Item findItem = em.find(Item.class, movie.getId());
            System.out.println("findItem: " + findItem);

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
