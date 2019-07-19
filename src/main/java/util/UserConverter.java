package util;

import dao.Dao;
import domen.User;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
public class UserConverter implements Converter, Serializable {

    @EJB
    Dao dao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<User> users = dao.getAllUsers();
        if (value.isEmpty()) {
            return null;
        }
        for (User u : users) {
            if (u.getId().toString().equals(value)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof User) {
            User user = (User) value;
            return user.getId().toString();
        }
        return value.toString();
    }
}
