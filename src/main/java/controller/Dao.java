package controller;

import domen.Book;
import domen.User;

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
        entityManager.clear();
        Query query = entityManager.createQuery("from Book");
        List resultList = query.getResultList();
        return resultList;
    }

    public List<Book> searchBooks(Integer bookUser, Date fromDate, Date toDate) {
        entityManager.clear();
        Query query = entityManager.createQuery("from Book where bookUser=:bookUser and date>=:fromDate and date<=:toDate");
        query.setParameter("bookUser", bookUser);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        return query.getResultList();
    }

    public int updateBookId(Object oldValue, Object newValue) {
        entityManager.clear();
        Query query = entityManager.createQuery("update Book set id=:newVal where id=:oldVal");
        query.setParameter("newVal", newValue);
        query.setParameter("oldVal", oldValue);
        return query.executeUpdate();
    }

    public int updateBookTitle(Object oldValue, Object newValue) {
        entityManager.clear();
        Query query = entityManager.createQuery("update Book set title=:newVal where title=:oldVal");
        query.setParameter("newVal", newValue);
        query.setParameter("oldVal", oldValue);
        return query.executeUpdate();
    }

    public int updateBookDate(Object oldValue, Object newValue) {
        entityManager.clear();
        Query query = entityManager.createQuery("update Book set date=:newVal where date=:oldVal");
        query.setParameter("newVal", newValue);
        query.setParameter("oldVal", oldValue);
        return query.executeUpdate();
    }

    public int updateBookUser(Integer bookId, Object newValue) {
        entityManager.clear();
        User newUser = (User) newValue;
        Query query = entityManager.createNativeQuery("UPDATE Book set bookUser=? where id=?");
        query.setParameter(1, newUser.getId());
        query.setParameter(2, bookId);
        return query.executeUpdate();
    }
}
