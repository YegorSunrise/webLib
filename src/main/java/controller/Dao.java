package controller;

import domen.Book;
import domen.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Stateless
public class Dao {

    @PersistenceContext(unitName = "simple")
    EntityManager entityManager;

    public void addUser(User user) {
        entityManager.persist(user);
    }

    public List<User> getAllUsers() {
        Query query = entityManager.createQuery("from User");
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
        Query query = entityManager.createQuery("from Book");
        return query.getResultList();
    }

    public List<Book> searchBooks(String bookUser, Date fromDate, Date toDate) {
        System.out.println("-----------IN SEARCH BOOK METHOD---------------");

        Query query = entityManager.createQuery("from Book where bookUser=:bookUser and date>=:fromDate and date<=:toDate");
        System.out.println("---------------QUERY SUCCESSFUL");
        query.setParameter("bookUser",bookUser);
        query.setParameter("fromDate",fromDate);
        query.setParameter("toDate",toDate);

        return query.getResultList();
    }
}
