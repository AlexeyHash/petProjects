package alexey.springJPA.library.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Поле имя не может быть пустым.")
    @Size(min = 2, max = 70, message = "Длина имени можеть быть от 2 до 70 символов.")
    @Column(name = "name")
    private String name;
    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
    @Column(name = "birthday")
    private int birthday;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(int id, String name, int birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }
    public Person(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
