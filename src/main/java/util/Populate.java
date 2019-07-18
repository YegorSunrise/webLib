package util;

import controller.Dao;
import domen.Book;
import domen.User;
import lombok.Data;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@ManagedBean
@Data
public class Populate {

    @EJB
    Dao dao;

    @EJB
    GlobalBookList globalBookList;

    private User user = new User();
    private int usersAmount;
    private int booksAmount;

    public void populate() {
        for (int i = 0; i < usersAmount; i++) {
            User user = new User();
            user.setFullName(InfoGenerator.getFullNames().get(new Random().nextInt(InfoGenerator.getFullNames().size())));
            user.setLogin(UUID.randomUUID().toString().substring(0, 4));
            user.setPass(UUID.randomUUID().toString().substring(0, 16));
            dao.addUser(user);
        }
        List<User> usersList = dao.getAllUsers();

        for (int i = 0; i < booksAmount; i++) {
            Book book = new Book();
            book.setTitle(InfoGenerator.getTitles().get(new Random().nextInt(InfoGenerator.getTitles().size())));
            book.setDate(Date.from(InfoGenerator.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            book.setUser(usersList.get(new Random().nextInt(usersList.size())));
            dao.addBook(book);
        }
        globalBookList.setBooks(dao.getAllBooks());
    }

    private static class InfoGenerator {
        public static List<String> getFullNames() {
            String names = "Кризько Иван Федосиевич\n" +
                    "Яикбаева Виктория Павеловна\n" +
                    "Актжанов Вениамин Олегович\n" +
                    "Камбарова Влада Ростиславовна\n" +
                    "Фламин Эрнест Ираклиевич\n" +
                    "Мукосеев Никифор Епифанович\n" +
                    "Драгомирова Валерия Георгиевна\n" +
                    "Люба Максим Тарасович\n" +
                    "Окладникова Маргарита Василиевна\n" +
                    "Квасникова Раиса Афанасиевна\n" +
                    "Сёмина Регина Александровна\n" +
                    "Юнкин Венедикт Климентович\n" +
                    "Бойцов Аркадий Венедиктович\n" +
                    "Бебнева Эмилия Георгиевна\n" +
                    "Шурдукова Эмилия Леонидовна\n" +
                    "Борисюк Даниил Эрнестович\n" +
                    "Летова Анна Потаповна\n" +
                    "Угличинина Рада Борисовна\n" +
                    "Сабанцев Глеб Ильевич";
            String[] split = names.split("\n");
            return new ArrayList<>(Arrays.asList(split));
        }

        public static List<String> getTitles() {
            String title = "Возлюбленная огненного графа\n" +
                    "Кафедра бездомных адептов\n" +
                    "Крылья воздушной теплоты\n" +
                    "Моё наглое молчание\n" +
                    "Тёмная сторона белой мысли\n" +
                    "Страницы догоревшей эйфории\n" +
                    "Мир покинутой безмятежности\n" +
                    "Моя окончательная одиссея\n" +
                    "Мой наглый поиск\n" +
                    "Мой огромный мир\n" +
                    "Потрясающее лето\n" +
                    "Седьмый город\n" +
                    "Моя прекрасная борьба\n" +
                    "Заложница всемогущего волка\n" +
                    "Картины волшебной души\n" +
                    "Личная одиссея\n" +
                    "Моё триумфальное путешествие\n" +
                    "Призрачная площадь\n" +
                    "Бессмертная ловушка\n" +
                    "Громадное путешествие\n" +
                    "Тайный спор\n" +
                    "Армии племен пустыни\n" +
                    "Океан догоревшей глупости\n" +
                    "Интернет-друг\n" +
                    "Океан внутри храбрости\n" +
                    "Максимальный усилие\n" +
                    "Седьмая стена\n" +
                    "Служанка ненавистного варвара";
            String[] split = title.split("\n");
            return new ArrayList<>(Arrays.asList(split));
        }

        public static LocalDate getDate() {
            long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
            long maxDay = LocalDate.now().toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            return LocalDate.ofEpochDay(randomDay);
        }
    }
}

