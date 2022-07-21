package ru.alexey.spring.library.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;
    @NotEmpty(message = "Поле имя не может быть пустым.")
    @Size(min = 2, max = 70, message = "Дина имени можеть быть от 2 до 70 символов.")
    private String name;
    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
    private int birthday;

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
}
