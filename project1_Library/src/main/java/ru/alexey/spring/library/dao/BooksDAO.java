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
public class BooksDAO {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new BeanPropertyRowMapper<>(Book.class), id)
                .stream()
                .findAny()
                .orElse(null);
    }

    public void update(int id, Book book){
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE id=?",
                book.getTitle(),book.getAuthor(), book.getYear(), id);
    }

    public void appointBook(int id, Person person){
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", person.getId(), id);
    }

    public void release(int id){
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?",null,id);
    }

    public Optional<Person> getOwner(int id){
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.person_id = person.id WHERE book.id=?",
                        new BeanPropertyRowMapper<>(Person.class),id)
                .stream()
                .findAny();
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book(title, author, year) VALUES (?,?,?)",
                book.getTitle(),book.getAuthor(), book.getYear());
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }


}
