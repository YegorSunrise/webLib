package controller;

import domen.Book;
import domen.User;
import lombok.Data;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;


@ManagedBean
@Data
public class CdiUserController {

    @EJB
    EjbController ejbController;

    private User user = new User();
    private int usersAmount;
    private int booksAmount;
    private String userFullName;

    public void createUser() {
        ejbController.addUser(user);
    }

    public List<User> getUsersList() {
        return ejbController.getAllUsers();
    }

    public void clearUsers() {
        ejbController.clearTables();
    }

    public int getHighestId() {
        List<User> usersList = getUsersList();
        if (!usersList.isEmpty()) {
            return usersList.stream().max(Comparator.comparing(User::getId)).get().getId();
        }
        return -2;
    }

    public void populate() {
        for (int i = 0; i < usersAmount; i++) {
            User user = new User(
                    UUID.randomUUID().toString().substring(0, 8)
                    , UUID.randomUUID().toString().substring(0, 4).toUpperCase()
                    , UUID.randomUUID().toString().substring(0, 16));
            System.out.println("-----------------CREATE USER------------------");

            ejbController.addUser(user);

        }
        List<User> usersList = getUsersList();
       for(int i = 0; i < booksAmount; i++){
            Book book = new Book(
                    UUID.randomUUID().toString().substring(0, 20).toUpperCase()
                    , LocalDateTime.now()
                    , usersList.get(new Random().nextInt(usersList.size())));
            System.out.println("-----------------CREATE BOOK------------------");
            ejbController.addBook(book);
        }
    }

    public List<String> fullNameList() {
        return getUsersList().stream().map(User::getFullName).collect(Collectors.toList());
    }


}
