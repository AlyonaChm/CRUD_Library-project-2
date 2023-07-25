package ua.chmil.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.chmil.spring.models.Person;

import java.util.List;

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

    // Цей метод повертає або null, якщо Person з вказаним id не була знайдена. В реальному app можна замість null вказати об'єкт з помилкою.
    // або 1 рядок - власне, якщо Person з id вказаним id була знайдена
    // .findAny() повертає Optional
    public Person show(int id) {
        return jdbcTemplate.query("SELECT  * FROM Person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person VALUES (1, ?, ?, ?)", person.getName(),
                person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
       jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", updatedPerson.getName(),
               updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}