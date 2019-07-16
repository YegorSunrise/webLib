package cdi;

import controller.Dao;
import domen.User;
import lombok.Data;
import util.GlobalList;

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
    GlobalList globalList;

    public List<User> getUsersList() {
        return dao.getAllUsers();
    }

    public void clearTables() {
        globalList.setBooks(Collections.emptyList());
        dao.clearTables();
    }

}
