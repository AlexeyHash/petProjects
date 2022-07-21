package ru.alexey.spring.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alexey.spring.library.dao.PersonDAO;
import ru.alexey.spring.library.model.Person;

import java.util.Optional;
@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Optional<Person> optionalPerson = personDAO.showPersonByName(person.getName());
        if (optionalPerson.isPresent()){
            errors.rejectValue("name","","Данное имя уже зарегестрировано. Попробуйте снова.");
        }
    }
}
