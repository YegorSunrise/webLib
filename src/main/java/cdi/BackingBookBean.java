package cdi;

import controller.Dao;
import domen.Book;
import lombok.Data;
import util.GlobalList;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
@Data
public class BackingBookBean implements Serializable {

    @EJB
    Dao dao;

    @EJB
    GlobalList globalList;

    private List<Book> books;
    private Date fromDate;
    private Date toDate;
    private String bookUser;


    public List<Book> searchedBooks() {
        globalList.setBooks(dao.searchBooks(bookUser, fromDate, toDate));
        return books;
    }

    public List<Book> booksList() {
        books = globalList.getBooks();
        return books;
    }

}