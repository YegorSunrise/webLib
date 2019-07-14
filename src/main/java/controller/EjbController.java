package controller;

import model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Comparator;
import java.util.List;

@Stateless
public class EjbController {

    @PersistenceContext(unitName = "simple")
    EntityManager entityManager;

    public void addUser(String fullName, String login, String pass) {
        User user = new User();
        user.setFullName(fullName);
        user.setLogin(login);
        user.setPass(pass);
        entityManager.persist(user);
    }

    public List<User> getAll(){
        Query query = entityManager.createQuery("select user from User user");
        return query.getResultList();
    }

    public void clearTable() {
        entityManager.createNativeQuery("DELETE from User").executeUpdate();
    }


}
