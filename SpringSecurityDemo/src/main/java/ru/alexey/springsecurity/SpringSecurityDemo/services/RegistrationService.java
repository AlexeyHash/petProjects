package ru.alexey.springsecurity.SpringSecurityDemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexey.springsecurity.SpringSecurityDemo.models.Person;
import ru.alexey.springsecurity.SpringSecurityDemo.repositories.PeopleRepository;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void save(Person person) {
        String encode = passwordEncoder.encode(person.getPassword());
        person.setPassword(encode);
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
    }
}
