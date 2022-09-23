package ru.alexey.springsecurity.SpringSecurityDemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alexey.springsecurity.SpringSecurityDemo.models.Person;
import ru.alexey.springsecurity.SpringSecurityDemo.repositories.PeopleRepository;
import ru.alexey.springsecurity.SpringSecurityDemo.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> user = peopleRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(user.get());
    }
}
