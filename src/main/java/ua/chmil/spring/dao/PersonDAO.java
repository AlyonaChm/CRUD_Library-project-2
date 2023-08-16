package ua.chmil.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.chmil.spring.models.Book;
import ua.chmil.spring.models.Person;
import java.util.List;
import java.util.Optional;

@Component // помітили цією анотацією аби Spring міг створити bean цього класу
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class)); // new PersonMapper() - це RowMapper. Це такий об'єкт, який відображає рядки з таблиці в сутність - об'єкт класу Person
    }

    public Person show(int id_person) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id_person = ?", new Object[]{id_person}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person (name, birthYear) VALUES (?, ?)", person.getName(),
                person.getBirthYear());
    }

    public void update(int id_person, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, birthYear=? WHERE id_person=?", updatedPerson.getName(),
                updatedPerson.getBirthYear(), id_person);
    }

    public void delete(int id_person) {
        jdbcTemplate.update("DELETE FROM Person WHERE id_person=?", id_person);
    }


    public Optional<Person> getBookByName(String name) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Book> getBookByPersonId(int id_person) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_person=?", new Object[]{id_person},
                new BeanPropertyRowMapper<>(Book.class));
    }
}