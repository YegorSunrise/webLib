package cdi;

import dao.Dao;
import domen.Book;
import domen.User;
import lombok.Data;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.LazyDataModel;
import util.LazyLoad;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Named
@ViewScoped
public class DataBean implements Serializable {

    @EJB
    Dao dao;
    private Date fromDate;
    private Date toDate;
    private User user;
    private List<Book> filteredBookList;
    private LazyDataModel<Book> lazyModel = null;

    public void searchedLazyList() {
        lazyModel = new LazyLoad(dao, true, user.getId(), fromDate, toDate);
    }

    public void allBooks() {
        lazyModel = new LazyLoad(dao, false);
    }

    public List<User> getUsersList() {
        return dao.getAllUsers();
    }

    public void clearTables() {
        dao.clearTables();
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        String columnHeader = event.getColumn().getHeaderText();
        FacesContext context = FacesContext.getCurrentInstance();
        Book book = context.getApplication().evaluateExpressionGet(context, "#{book}", Book.class);
        if (newValue != null && !newValue.equals(oldValue)) {
            switch (columnHeader) {

                case "название":
                    int updateBookTitle = dao.updateBookTitle(book.getId(), newValue);
                    if (updateBookTitle > 0) {
                        updateSuccessful(oldValue, newValue, " изменено на ");
                        break;
                    } else {
                        updateRejected(newValue, " неверный параметр");
                    }

                case "дата":
                    int updateBookDate = dao.updateBookDate(book.getId(), newValue);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    if (updateBookDate > 0) {
                        updateSuccessful(dateFormat.format(oldValue), dateFormat.format(newValue), " изменена на ");
                        break;
                    } else {
                        updateRejected(newValue, " неверный параметр");
                    }

                case "код пользователя":
                    User newUser = (User) newValue;
                    int updateBookUser = dao.updateBookUser(book.getId(), newUser);
                    if (updateBookUser > 0) {
                        updateSuccessful(oldValue, newValue, " изменен на ");
                        break;
                    } else {
                        updateRejected(newValue, " неверный параметр");
                        break;
                    }
            }
        } else {
            updateRejected(newValue, " неверный параметр");
        }
    }

    private void updateRejected(Object newValue, String s) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Ошибка: ", newValue + s);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void updateSuccessful(Object oldValue, Object newValue, String s) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Успех: ", oldValue + "\n" + s + "\n" + newValue);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
