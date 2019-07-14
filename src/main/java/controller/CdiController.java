package controller;

import lombok.Data;
import model.User;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@Named
@SessionScoped
@Data
public class CdiController implements Serializable {

    @EJB
    EjbController ejbController;

    private String fullName;
    private String login;
    private String pass;

    public void createUser() {
        ejbController.addUser(fullName, login, pass);
    }

    public List<User> getUsersList() {
        return ejbController.getAll();
    }

    public void clearUsers() {
        ejbController.clearTable();
    }

    public int getHighestId() {
        List<User> usersList = getUsersList();
        if (!usersList.isEmpty()) {
            return usersList.stream().max(Comparator.comparing(User::getId)).get().getId();
        }
        return -2;
    }
}
