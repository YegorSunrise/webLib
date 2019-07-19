package dao;

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

    public void clearTables() {
        entityManager.createNativeQuery("DELETE from Book").executeUpdate();
        entityManager.createNativeQuery("DELETE from User").executeUpdate();
    }

    public void addBook(Book book) {
        entityManager.persist(book);
    }

    public List<Book> getAllBooks() {
        Query query = entityManager.createQuery("from Book");
        List resultList = query.getResultList();
        return resultList;
    }

    public List<Book> findBooks(int first, int pageSize) {
        Query query = entityManager.createQuery("select book from Book book");
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }


    public int getTotalBooksCount() {
        Query query = entityManager.createQuery("Select count(book.id) From Book book");
        return ((Long)query.getSingleResult()).intValue();
    }


    public List<User> getAllUsers() {
        Query query = entityManager.createQuery("from User");
        return query.getResultList();
    }

    public List<Book> searchBooks(Integer bookUser, Date fromDate, Date toDate, int first, int pageSize) {
        Query query = entityManager.createQuery("from Book where bookUser=:bookUser and date>=:fromDate and date<=:toDate");
        query.setParameter("bookUser", bookUser);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public int updateBookTitle(Integer bookId, Object newValue) {
        Query query = entityManager.createQuery("update Book set title=:newVal where id=:bookId");
        query.setParameter("newVal", newValue);
        query.setParameter("bookId", bookId);
        return query.executeUpdate();
    }

    public int updateBookDate(Integer bookId, Object newValue) {
        Query query = entityManager.createQuery("update Book set date=:newVal where id=:bookId");
        query.setParameter("newVal", newValue);
        query.setParameter("bookId", bookId);
        return query.executeUpdate();
    }

    public int updateBookUser(Integer bookId, Object newValue) {
        User newUser = (User) newValue;
        Query query = entityManager.createNativeQuery("UPDATE Book set bookUser=? where id=?");
        query.setParameter(1, newUser.getId());
        query.setParameter(2, bookId);
        return query.executeUpdate();
    }
}
