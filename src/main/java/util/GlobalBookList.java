package util;

import controller.Dao;
import domen.Book;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.List;

@Singleton
public class GlobalBookList {

    @EJB
    Dao dao;
    private List<Book> books;


    @PostConstruct
    public void init(){
        books = dao.getAllBooks();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
