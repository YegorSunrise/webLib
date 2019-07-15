package controller;

import domen.Book;
import domen.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class EjbController {

    @PersistenceContext(unitName = "simple")
    EntityManager entityManager;

    public void addUser(User user) {
        entityManager.persist(user);
    }

    public List<User> getAllUsers() {
        Query query = entityManager.createQuery("select user from User user");
        return query.getResultList();
    }

    public void clearTables() {
        entityManager.createNativeQuery("DELETE from Book").executeUpdate();
        entityManager.createNativeQuery("DELETE from User").executeUpdate();
    }

    public void addBook(Book book) {
        entityManager.persist(book);
    }

    public List<Book> getAllBooks() {
        Query query = entityManager.createQuery("select book from Book book");
        return query.getResultList();
    }
}
