package util;

import domen.Book;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBook {


    public static Book getBook() {
        Book book = new Book();
        book.setTitle(new NameGenerator().getAcronym());
        book.setDate(Date.from(getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return book;
    }

    private static LocalDate getDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    private static class NameGenerator {
        private String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"};

        private String acronym;

        private NameGenerator() {
            this.acronym = "awesome story about " +
                    alphabet[new Random().nextInt(26)] +
                    alphabet[new Random().nextInt(26)] +
                    alphabet[new Random().nextInt(26)] +
                    alphabet[new Random().nextInt(26)];
        }

        private String getAcronym() {
            return acronym;
        }
    }
}
