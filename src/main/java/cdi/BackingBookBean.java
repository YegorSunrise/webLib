package cdi;

import dao.Dao;
import domen.Book;
import domen.User;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import util.GlobalBookList;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
@Data
public class BackingBookBean implements Serializable {

    @EJB
    Dao dao;

    @EJB
    GlobalBookList globalBookList;

    private Date fromDate;
    private Date toDate;
    private User user;
    private List<Book> filteredBookList;
    private Integer bookId;

    public void searchedBooks() {
        if (user == null || fromDate == null || toDate == null) {
            globalBookList.setBooks(Collections.emptyList());
            clearFilter();
        } else {
            globalBookList.setBooks(dao.searchBooks(user.getId(), fromDate, toDate));
            clearFilter();
        }
    }

    public List<Book> changedBookList() {
        List<Book> books = globalBookList.getBooks();
        clearFilter();
        return books;
    }

    public void bookList() {
        globalBookList.setBooks(dao.getAllBooks());
        clearFilter();
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        String columnHeader = event.getColumn().getHeaderText();
        FacesContext context = FacesContext.getCurrentInstance();
        Book book = context.getApplication().evaluateExpressionGet(context, "#{book}", Book.class);
        if (newValue != null && !newValue.equals(oldValue)) {
            switch (columnHeader) {
                case "код книги":
                    List<Integer> allBooksId = dao.getAllBooks().stream().map(Book::getId).collect(Collectors.toList());
                    if (allBooksId.contains((Integer) newValue)) {
                        updateRejected(newValue, " id уже существует");
                        break;
                    } else {
                        int updateBookId = dao.updateBookId(oldValue, newValue);
                        if (updateBookId > 0) {
                            updateSuccessful(columnHeader.toLowerCase(), newValue, " изменен на ");
                            break;
                        }
                    }

                case "название":
                    int updateBookTitle = dao.updateBookTitle(oldValue, newValue);
                    if (updateBookTitle > 0) {
                        updateSuccessful(oldValue, newValue, " изменено на ");
                        break;
                    } else {
                        updateRejected(newValue, " неверный параметр");
                    }

                case "дата":
                    int updateBookDate = dao.updateBookDate(oldValue, newValue);
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

    private void clearFilter() {
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:bookList");
        if (!dataTable.getFilters().isEmpty()) {
            dataTable.reset();
            PrimeFaces.current().ajax().update("form:bookList");
        }
    }
}