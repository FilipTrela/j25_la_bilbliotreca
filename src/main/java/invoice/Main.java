package invoice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private final static Scanner SCANNER = new Scanner(System.in);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/MM/yyyy");

    public static void main(String[] args) {
        System.out.println("Witam w księgarni! Czym mogę służyć?");
        EntityDao dao = new EntityDao();
        String komenda;
        do {
            komenda = SCANNER.nextLine();
            if (komenda.equalsIgnoreCase("dodaj autora"))
                addAuthor(dao);
            else if (komenda.equalsIgnoreCase("test"))
                addTest(dao);
            else if (komenda.equalsIgnoreCase("list autor"))
                dao.getAll(Author.class).forEach(System.out::println);
            else if (komenda.equalsIgnoreCase("usun autora"))
                removeAuthor(dao);
            else if (komenda.equalsIgnoreCase("edit autor"))
                editAuthor(dao);
            else if (komenda.equalsIgnoreCase("autor ksiazka"))
                makeRelationAuthorBook(dao);
            else if (komenda.equalsIgnoreCase("dodaj ksiazke"))
                addBook(dao);
            else if (komenda.equalsIgnoreCase("list ksiazki"))
                dao.getAll(Book.class).forEach(System.out::println);
            else if (komenda.equalsIgnoreCase("usun ksiake"))
                removeBook(dao);
            else if (komenda.equalsIgnoreCase("dodaj klienta"))
                addReader(dao);
            else if (komenda.equalsIgnoreCase("list klient"))
                dao.getAll(Reader.class).forEach(System.out::println);
            else if (komenda.equalsIgnoreCase("usun klient"))
                removeReader(dao);
            else if (komenda.equalsIgnoreCase("edit klient"))
                editReader(dao);
            else if (komenda.equalsIgnoreCase("dodaj wypożyczenie"))
                addBookLent(dao);
            else if (komenda.equalsIgnoreCase("znajdz autorow po nazwisku"))
                dao.getAuthorsBySurname(askLastName()).forEach(System.out::println);
            else if (komenda.equalsIgnoreCase("znajdz klientow po nazwisku"))
                dao.getReaderBySurname(askLastName()).forEach(System.out::println);
            else if (komenda.equalsIgnoreCase("znajdz klienta po id"))
                System.out.println(dao.getById(Reader.class, askID("klienta")).get().toString());
            else if (komenda.equalsIgnoreCase("ksiazki klienta"))
                listAllBookByReader(dao);
            else if (komenda.equalsIgnoreCase("ksiazki nie zwrocne"))
                listAllBookByReaderThatNotReturned(dao);
            else if(komenda.equalsIgnoreCase("ksiazki dostepne")){
                listAllAvailibleBook(dao);
            }


        } while (!komenda.equalsIgnoreCase("quit"));
    }

    private static void listAllAvailibleBook(EntityDao dao) {
        dao.getAll(Book.class).stream()
                .filter(book -> book.getNumberOfAvailableCopies()>book.getNumberIfBorrowedCopies())
                .forEach(System.out::println);dd
    }

    private static void listAllBookByReaderThatNotReturned(EntityDao dao) {
        Reader reader = dao.getById(Reader.class, askID("klienta")).get();
        reader.getLents().stream().filter(bookLent -> bookLent.getDateReturned() == null)
                .map(BookLent::getBook).forEach(System.out::println);
    }

    private static void listAllBookByReader(EntityDao dao) {
        Reader reader = dao.getById(Reader.class, askID("klienta")).get();
        reader.getLents().stream().map(BookLent::getBook).forEach(System.out::println);
    }

    private static void addBookLent(EntityDao dao) {
        BookLent bookLent = new BookLent();
        bookLent.setClient(dao.getById(Reader.class, askID("klienta")).get());
        bookLent.setBook(dao.getById(Book.class, askID("ksiażki")).get());
        dao.saveOrUpdate(bookLent);
    }

    private static void addTest(EntityDao dao) {
        dao.saveOrUpdate(new Author("Filip", "Trela", LocalDate.parse("26/02/1992", FORMATTER)));
        dao.saveOrUpdate(new Author("Michał", "Kichał", LocalDate.parse("11/11/1987", FORMATTER)));
        dao.saveOrUpdate(new Author("Paweł", "Gaweł", LocalDate.parse("17/01/1984", FORMATTER)));
        dao.saveOrUpdate(new Author("Jacek", "Wacek", LocalDate.parse("30/01/1999", FORMATTER)));
        dao.saveOrUpdate(new Author("Michalina", "Popelina", LocalDate.parse("28/07/1976", FORMATTER)));
        dao.saveOrUpdate(new Author("Ewa", "Chodakowska", LocalDate.parse("03/10/1954", FORMATTER)));

        Book book1 = new Book("Harry Potter 1", LocalDate.parse("02/03/2000", FORMATTER), 240, 5);
        Book book2 = new Book("Harry Potter 2", LocalDate.parse("11/05/2004", FORMATTER), 321, 6);
        Book book3 = new Book("Harry Potter 3", LocalDate.parse("30/11/2010", FORMATTER), 319, 3);
        Book book4 = new Book("Metody Kichania", LocalDate.parse("01/05/1994", FORMATTER), 121, 10);
        Book book5 = new Book("Pawełek cz. 1", LocalDate.parse("01/09/2005", FORMATTER), 640, 11);
        Book book6 = new Book("Pawełek cz. 2", LocalDate.parse("18/10/2006", FORMATTER), 356, 4);
        Book book7 = new Book("Jacek i jego wacek", LocalDate.parse("01/01/2011", FORMATTER), 13, 5);
        Book book8 = new Book("Wacek i jego Jacek", LocalDate.parse("02/03/2018", FORMATTER), 11, 6);
        Book book9 = new Book("Przygody popeliny", LocalDate.parse("23/09/1984", FORMATTER), 564, 20);
        Book book10 = new Book("Gwiazdy kina", LocalDate.parse("24/01/1975", FORMATTER), 764, 5);
        Book book11 = new Book("Gwiazdy sportu", LocalDate.parse("02/12/1981", FORMATTER), 752, 6);
        Book book12 = new Book("Gwiazdy nieba", LocalDate.parse("12/06/1963", FORMATTER), 5634, 7);

        dao.saveOrUpdate(book1);
        dao.saveOrUpdate(book2);
        dao.saveOrUpdate(book3);
        dao.saveOrUpdate(book4);
        dao.saveOrUpdate(book5);
        dao.saveOrUpdate(book6);
        dao.saveOrUpdate(book7);
        dao.saveOrUpdate(book8);
        dao.saveOrUpdate(book9);
        dao.saveOrUpdate(book10);
        dao.saveOrUpdate(book11);
        dao.saveOrUpdate(book12);

        makeRelation(dao, 1l, 1l);
        makeRelation(dao, 1l, 2l);
        makeRelation(dao, 1l, 3l);
        makeRelation(dao, 2l, 4l);
        makeRelation(dao, 3l, 5l);
        makeRelation(dao, 3l, 6l);
        makeRelation(dao, 4l, 7l);
        makeRelation(dao, 4l, 8l);
        makeRelation(dao, 5l, 9l);
        makeRelation(dao, 6l, 10l);
        makeRelation(dao, 6l, 11l);
        makeRelation(dao, 6l, 12l);

        Reader reader1 = new Reader("Maćek", "Wałensa", "RTA43532");
        Reader reader2 = new Reader("Maćek", "Lech", "STR355464");
        Reader reader3 = new Reader("Lech", "Wałensa", "STR241265");
        Reader reader4 = new Reader("Jacek", "Placek", "WCA44122");
        Reader reader5 = new Reader("Kondrad", "Michalak", "SRT4356");
        Reader reader6 = new Reader("Angelika", "Zniebaspadła", "SRA45642");
        Reader reader7 = new Reader("Marta", "Obdarta", "TYL123412");
        Reader reader8 = new Reader("Frodo", "Baggins", "TYU5896243");
        Reader reader9 = new Reader("Sam", "Ginłi", "TYR6124812");
        Reader reader10 = new Reader("Gandalf", "Szary", "RETE2183912");
        Reader reader11 = new Reader("Galndalf", "Biały", "21421412");
        Reader reader12 = new Reader("Saruman", "Biały", "RTY14124");
        dao.saveOrUpdate(reader1);
        dao.saveOrUpdate(reader2);
        dao.saveOrUpdate(reader3);
        dao.saveOrUpdate(reader4);
        dao.saveOrUpdate(reader5);
        dao.saveOrUpdate(reader6);
        dao.saveOrUpdate(reader7);
        dao.saveOrUpdate(reader8);
        dao.saveOrUpdate(reader9);
        dao.saveOrUpdate(reader10);
        dao.saveOrUpdate(reader11);
        dao.saveOrUpdate(reader12);
        dao.saveOrUpdate(new BookLent(reader1, book1));
        dao.saveOrUpdate(new BookLent(reader1, book2));
        dao.saveOrUpdate(new BookLent(reader1, book3));
        dao.saveOrUpdate(new BookLent(reader1, book6));
        dao.saveOrUpdate(new BookLent(reader1, book7));
        dao.saveOrUpdate(new BookLent(reader2, book1));
        dao.saveOrUpdate(new BookLent(reader2, book2));
        dao.saveOrUpdate(new BookLent(reader2, book3));
        dao.saveOrUpdate(new BookLent(reader2, book5));
        dao.saveOrUpdate(new BookLent(reader3, book12));
        dao.saveOrUpdate(new BookLent(reader4, book11));
        dao.saveOrUpdate(new BookLent(reader4, book10));
        dao.saveOrUpdate(new BookLent(reader4, book9));
        dao.saveOrUpdate(new BookLent(reader4, book5));
        dao.saveOrUpdate(new BookLent(reader4, book1));
        dao.saveOrUpdate(new BookLent(reader5, book10));
        dao.saveOrUpdate(new BookLent(reader5, book12));
        dao.saveOrUpdate(new BookLent(reader5, book11));
        dao.saveOrUpdate(new BookLent(reader6, book6));
        dao.saveOrUpdate(new BookLent(reader6, book5));
        dao.saveOrUpdate(new BookLent(reader6, book4));
        dao.saveOrUpdate(new BookLent(reader6, book3));
        dao.saveOrUpdate(new BookLent(reader6, book2));
        dao.saveOrUpdate(new BookLent(reader6, book1));
        dao.saveOrUpdate(new BookLent(reader7, book1));
        dao.saveOrUpdate(new BookLent(reader7, book2));
        dao.saveOrUpdate(new BookLent(reader7, book10));
        dao.saveOrUpdate(new BookLent(reader7, book8));
        dao.saveOrUpdate(new BookLent(reader8, book7));
        dao.saveOrUpdate(new BookLent(reader8, book9));
        dao.saveOrUpdate(new BookLent(reader8, book10));
        dao.saveOrUpdate(new BookLent(reader8, book11));
        dao.saveOrUpdate(new BookLent(reader8, book12));
        dao.saveOrUpdate(new BookLent(reader9, book7));
        dao.saveOrUpdate(new BookLent(reader9, book6));
        dao.saveOrUpdate(new BookLent(reader9, book5));
        dao.saveOrUpdate(new BookLent(reader10, book12));
        dao.saveOrUpdate(new BookLent(reader10, book11));
        dao.saveOrUpdate(new BookLent(reader10, book10));
        dao.saveOrUpdate(new BookLent(reader10, book7));
        dao.saveOrUpdate(new BookLent(reader10, book5));
        dao.saveOrUpdate(new BookLent(reader10, book3));
        dao.saveOrUpdate(new BookLent(reader11, book1));
        dao.saveOrUpdate(new BookLent(reader11, book1));
        dao.saveOrUpdate(new BookLent(reader11, book2));
        dao.saveOrUpdate(new BookLent(reader12, book5));
        dao.saveOrUpdate(new BookLent(reader12, book8));
        dao.saveOrUpdate(new BookLent(reader12, book7));
        dao.saveOrUpdate(new BookLent(reader12, book6));
        dao.saveOrUpdate(new BookLent(reader12, book4));
        dao.saveOrUpdate(new BookLent(reader12, book12));

    }

    private static void makeRelation(EntityDao dao, Long idA, Long idB) {
        Author author = dao.getById(Author.class, idA).get();
        Book book = dao.getById(Book.class, idB).get();
        author.getBooks().add(book);
        dao.saveOrUpdate(author);

    }

    private static void makeRelationAuthorBook(EntityDao dao) {
        Author author = dao.getById(Author.class, askID("autora")).get();
        Book book = dao.getById(Book.class, askID("książki")).get();
        author.getBooks().add(book);
        dao.saveOrUpdate(author);
    }


    private static void editReader(EntityDao dao) {
        Reader reader = dao.getById(Reader.class, askID("klienta")).get();
        System.out.println("Co chcesz zmodyfikować ? :");
        String komenda = SCANNER.nextLine();
        if (komenda.equalsIgnoreCase("imie"))
            reader.setName(askName());
        else if (komenda.equalsIgnoreCase("nazwisko"))
            reader.setSurname(askLastName());
        else if (komenda.equalsIgnoreCase("id"))
            reader.setIdNumber(askIdNumber());
        else
            System.out.println("Zła komenda ! ");


    }

    private static void removeReader(EntityDao dao) {
        dao.delete(Reader.class, askID("klienta"));
    }

    private static void addReader(EntityDao dao) {
        dao.saveOrUpdate(makeReader());
    }

    private static Reader makeReader() {
        Reader reader = new Reader();
        reader.setName(askName());
        reader.setSurname(askLastName());
        reader.setIdNumber(askIdNumber());
        return reader;
    }

    private static String askIdNumber() {
        System.out.println("Podaj numer dowodu : ");
        return SCANNER.nextLine();
    }

    private static void removeBook(EntityDao dao) {
        dao.delete(Book.class, askID("ksiażki"));
    }

    private static void addBook(EntityDao dao) {
        dao.saveOrUpdate(makeBook());
    }

    private static Book makeBook() {
        Book book = new Book();
        book.setTitle(askTitile());
        book.setNumberOfPage(askNumberOfPAge());
        book.setYearWritten(askYearWritten());
        book.setNumberOfAvailableCopies(askNumberOfAvailableCopies());
        return book;
    }

    private static int askNumberOfAvailableCopies() {
        System.out.println("Podaj liczbe kopi : ");
        return Integer.parseInt(SCANNER.nextLine());
    }

    private static LocalDate askYearWritten() {
        System.out.println("Data wydania : ");
        return LocalDate.parse(SCANNER.nextLine(), FORMATTER);
    }

    private static int askNumberOfPAge() {
        System.out.println("Podaj ilość stron");
        return Integer.parseInt(SCANNER.nextLine());
    }

    private static String askTitile() {
        System.out.println("Podaj tytuł :");
        return SCANNER.nextLine();
    }

    private static void editAuthor(EntityDao dao) {
        Author author = dao.getById(Author.class, askID("autora")).get();
        System.out.println("Co chcesz zmodyfikować ? :");
        String komenda = SCANNER.nextLine();
        if (komenda.equalsIgnoreCase("imie"))
            author.setName(askName());
        else if (komenda.equalsIgnoreCase("nazwisko"))
            author.setLastName(askLastName());
        else if (komenda.equalsIgnoreCase("rok"))
            author.setDateOfBirth(setDateOfBirth());
        else
            System.out.println("Zła komenda ! ");

    }

    private static void removeAuthor(EntityDao dao) {
        dao.delete(Author.class, askID("autora"));
    }

    private static Long askID(String name) {
        System.out.println("Podaj ID " + name + " : ");
        return Long.parseLong(SCANNER.nextLine());
    }

    private static void addAuthor(EntityDao dao) {
        dao.saveOrUpdate(makeAuthor());
    }

    private static Author makeAuthor() {
        Author author = new Author();
        author.setName(askName());
        author.setLastName(askLastName());
        author.setDateOfBirth(setDateOfBirth());
        return author;
    }

    private static LocalDate setDateOfBirth() {
        System.out.println("Podaj date urodzenia [d/MM/yyyy]: ");
        return LocalDate.parse(SCANNER.nextLine(), FORMATTER);

    }

    private static String askLastName() {
        System.out.println("Podaj nazwisko : ");
        return SCANNER.nextLine();
    }

    private static String askName() {
        System.out.println("Podaj imię : ");
        return SCANNER.nextLine();
    }

}
