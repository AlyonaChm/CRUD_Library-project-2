package ua.chmil.spring.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.chmil.spring.models.Book;
import ua.chmil.spring.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class)); // new PersonMapper() - це RowMapper. Це такий об'єкт, який відображає рядки з таблиці в сутність - об'єкт класу Person
    }

    public Book show(int id_book) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_book = ?", new Object[]{id_book}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book (title, author, publishedYear) VALUES (?, ?, ?)", book.getTitle(),
                book.getAuthor(), book.getPublishedYear());
    }

    public void update(int id_book, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, publishedYear=? WHERE id_book=?", updatedBook.getTitle(),
                updatedBook.getAuthor(), updatedBook.getPublishedYear(), id_book);
    }

    public void delete(int id_book) {
        jdbcTemplate.update("DELETE FROM Book WHERE id_book=?", id_book);
    }


    //Join таблиці Book та Person і отримуємо людину, якій належить книга з вказаним id_book
    public Optional<Person> getBookOwner(int id_person) {
        return jdbcTemplate.query("SELECT Person. * FROM Book JOIN Person ON Book.id_person = Person.id_person" +
                        " WHERE Book.id_book=?", new Object[]{id_person}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void release(int id_book) {
        jdbcTemplate.update("UPDATE Book SET id_person=NULL WHERE id_book=?", id_book);
    }

    public void assign(int id_book, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET id_person=? WHERE id_book=?", selectedPerson.getId_person(), id_book);
    }
}