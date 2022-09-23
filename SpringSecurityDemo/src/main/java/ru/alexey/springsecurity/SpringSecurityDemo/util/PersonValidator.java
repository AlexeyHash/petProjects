package ru.alexey.springsecurity.SpringSecurityDemo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alexey.springsecurity.SpringSecurityDemo.models.Person;
import ru.alexey.springsecurity.SpringSecurityDemo.repositories.PeopleRepository;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> foundPerson = peopleRepository.findByUsername(person.getUsername());
        if (foundPerson.isPresent()){
            errors.rejectValue("username","","Данное имя пользователя уже существует");
        }
    }
}
