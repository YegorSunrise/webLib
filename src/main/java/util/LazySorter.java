package util;

import domen.Book;
import domen.User;
import org.primefaces.model.SortOrder;

import java.lang.reflect.Field;
import java.util.Comparator;

public class LazySorter implements Comparator<Book> {

    private String sortField;

    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Book o1, Book o2) {
        Object value1;
        Object value2;
        try {
            if (sortField.equals("user.id")) {
                Field field1 = o1.getClass().getDeclaredField("user");
                Field field2 = o2.getClass().getDeclaredField("user");
                field1.setAccessible(true);
                field2.setAccessible(true);
                User user1 = (User) field1.get(o1);
                User user2 = (User) field1.get(o2);
                value1 = user1.getId();
                value2 = user2.getId();
            } else {
                Field field1 = o1.getClass().getDeclaredField(sortField);
                Field field2 = o2.getClass().getDeclaredField(sortField);
                field1.setAccessible(true);
                field2.setAccessible(true);
                value1 = field1.get(o1);
                value2 = field2.get(o2);
            }
            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (IllegalAccessException | NoSuchFieldException e) {
           throw new RuntimeException("LazySorter exception", e);
        }
    }
}