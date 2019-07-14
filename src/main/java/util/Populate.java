package util;

import model.User;

import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.io.Serializable;
import java.util.UUID;


public class Populate {

    @PersistenceUnit(unitName = "simple")
    private static EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setFullName(UUID.randomUUID().toString().substring(0, 8));
            user.setLogin(UUID.randomUUID().toString().substring(0, 4).toUpperCase());
            user.setPass(UUID.randomUUID().toString().substring(0, 16));
            entityManager.persist(user);
        }


    }
}
