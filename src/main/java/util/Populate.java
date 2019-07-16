package util;

import controller.Dao;
import domen.Book;
import domen.User;
import lombok.Data;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@ManagedBean
@Data
public class Populate {

    @EJB
    Dao dao;

    @EJB
    GlobalList globalList;

    private User user = new User();
    private int usersAmount;
    private int booksAmount;

    public void populate() {
        for (int i = 0; i < usersAmount; i++) {
            User user = new User(
                    UUID.randomUUID().toString().substring(0, 3).toUpperCase()
                    , UUID.randomUUID().toString().substring(0, 4)
                    , UUID.randomUUID().toString().substring(0, 16));
            System.out.println("-----------------CREATE USER------------------");

            dao.addUser(user);

        }
        List<User> usersList = dao.getAllUsers();
        for (int i = 0; i < booksAmount; i++) {
            Book book = RandomBook.getBook();
            book.setUser(usersList.get(new Random().nextInt(usersList.size())));
            System.out.println("-----------------CREATE BOOK------------------");
            dao.addBook(book);
        }
        globalList.setBooks(dao.getAllBooks());
    }
}
