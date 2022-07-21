package ru.alexey.spring.library.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alexey.spring.library.model.Book;
import ru.alexey.spring.library.model.Person;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream()
                .findAny()
                .orElse(null);
    }

    public Optional<Person> showPersonByName(String name){
        return jdbcTemplate.query("SELECT * FROM person where person.name=?",
                        new BeanPropertyRowMapper<>(Person.class), name)
                .stream()
                .findAny();
    }

    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE person SET name=?, birthday=? WHERE id=?",
                                            person.getName(), person.getBirthday(), id);
    }
    public List<Book> getBooks(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?",new BeanPropertyRowMapper<>(Book.class), id);
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO person(name, birthday) VALUES (?,?)",
                person.getName(),person.getBirthday());
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

}
