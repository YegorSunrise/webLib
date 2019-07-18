package cdi;

import dao.Dao;
import domen.User;
import lombok.Data;
import util.GlobalBookList;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named
@Data
@SessionScoped
public class BackingUserBean implements Serializable {

    private User user;
    private String fullName;
    private int userId;

    @EJB
    Dao dao;

    @EJB
    GlobalBookList globalBookList;

    public List<User> getUsersList() {
        return dao.getAllUsers();
    }

    public void clearTables() {
        globalBookList.setBooks(Collections.emptyList());
        dao.clearTables();
    }
}
