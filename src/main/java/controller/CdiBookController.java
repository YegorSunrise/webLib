package controller;

import domen.Book;
import domen.User;
import lombok.Data;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean
@Data
public class CdiBookController {

    @EJB
    EjbController ejbController;

    private Book book = new Book();


    public void createBook(){
        ejbController.addBook(book);
    }

    public List<Book> getBooksList() {
        return ejbController.getAllBooks();
    }
}
