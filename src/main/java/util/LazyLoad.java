package util;

import dao.Dao;
import domen.Book;
import domen.User;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class LazyLoad extends LazyDataModel<Book> {

    private Dao dao;
    private boolean isSearched;
    private Integer bookUserId;
    private Date fromDate;
    private Date toDate;
    private List<Book> lazyBookList;

    public LazyLoad(Dao dao, boolean isSearched) {
        this.isSearched = isSearched;
        this.dao = dao;
    }

    public LazyLoad(Dao dao, boolean isSearched, Integer bookUserId, Date fromDate, Date toDate) {
        this(dao, isSearched);
        this.bookUserId = bookUserId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public List<Book> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Book> filteredList = new ArrayList<>();
        if (!isSearched) {
            lazyBookList = dao.findBooks(first, pageSize);
        } else {
            if (bookUserId == null || fromDate == null || toDate == null) {
                lazyBookList = Collections.emptyList();
            } else {
                lazyBookList = dao.searchBooks(bookUserId, fromDate, toDate, first, pageSize);
            }
        }
        if (sortField != null) {
            lazyBookList.sort(new LazySorter(sortField, sortOrder));
        }
        String filterValue = null;
        for (Book book : lazyBookList) {
            boolean match = true;
            if (filters != null) {
                for (String value : filters.keySet()) {
                    try {
                        filterValue = (String) filters.get(value);
                        Field[] declaredFields = book.getClass().getDeclaredFields();
                        for (Field f : declaredFields) {
                            f.setAccessible(true);
                            switch (f.getName()) {
                                case "id":
                                    Integer id = (Integer) f.get(book);
                                    if (filterValue == null || id.toString().startsWith(filterValue)) {
                                        match = true;
                                        break;
                                    } else {
                                        match = false;
                                    }
                                    break;

                                case "title":
                                    String title = f.get(book).toString();
                                    String[] sTitle = title.split(" ");
                                    for (String s : sTitle) {
                                        s = s.replaceAll("[^\\p{L}\\p{Nd}]+", "");
                                        if (filterValue == null ||
                                                s.toLowerCase().startsWith(filterValue.toLowerCase())) {
                                            match = true;
                                            break;
                                        } else {
                                            match = false;
                                        }
                                    }
                                    break;

                                case "date":
                                    Date date = (Date) f.get(book);
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    String d = dateFormat.format(date);
                                    String[] sDate = d.split("-");
                                    for (String s : sDate) {
                                        if (filterValue == null || s.startsWith(filterValue)) {
                                            match = true;
                                            break;
                                        } else {
                                            match = false;
                                        }
                                    }
                                    break;

                                case "user":
                                    User user = (User) f.get(book);
                                    if (filterValue == null || user.getId().toString().startsWith(filterValue)) {
                                        match = true;
                                        break;
                                    } else {
                                        match = false;
                                    }
                                    break;
                            }
                            if (match) {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        match = false;
                    }

                    if (match) {
                        filteredList.add(book);
                    }
                }
            }
        }
        if (filterValue != null) {
            lazyBookList = filteredList;
        }
        if (getRowCount() <= 0) {
            setRowCount(dao.getTotalBooksCount());
        }
        setPageSize(pageSize);
        return lazyBookList;
    }

    @Override
    public Book getRowData(String rowKey) {
        for (Book book : lazyBookList) {
            if (book.getId().equals(Integer.valueOf(rowKey)))
                return book;
        }
        return null;
    }

    @Override
    public Object getRowKey(Book book) {
        return book.getId();
    }
}
