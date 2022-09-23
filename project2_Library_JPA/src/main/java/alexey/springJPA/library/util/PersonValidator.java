package alexey.springJPA.library.util;

import alexey.springJPA.library.models.Person;
import alexey.springJPA.library.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Optional<Person> optionalPerson = peopleService.findByName(person.getName());
        if (optionalPerson.isPresent()){
            errors.rejectValue("name","","Данное имя уже зарегестрировано. Попробуйте снова.");
        }
    }
}
