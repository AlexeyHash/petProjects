package alexey.springJPA.library.services;

import alexey.springJPA.library.models.Book;
import alexey.springJPA.library.models.Person;
import alexey.springJPA.library.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> findByName(String name) {
        return peopleRepository.findByName(name);
    }

    public List<Book> getBooks(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            for (Book book : person.get().getBooks()){
                LocalDate dateTaken = book.getTakenDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (LocalDate.now().isAfter(dateTaken.plusDays(10))){
                    book.setExpired(true);
                }
            }
            return person.get().getBooks();
        }
        else
            return Collections.emptyList();
    }

}


