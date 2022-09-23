package alexey.springJPA.library.controllers;

import alexey.springJPA.library.models.Book;
import alexey.springJPA.library.models.Person;
import alexey.springJPA.library.services.BooksService;
import alexey.springJPA.library.services.PeopleService;
import alexey.springJPA.library.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PersonValidator personValidator;
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PersonValidator personValidator, PeopleService peopleService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;

    }

    @GetMapping()
    public List<Person> index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return peopleService.findAll();
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        Person person = peopleService.findOne(id);
        List<Book> books = peopleService.getBooks(person.getId());
        model.addAttribute("person", person);
        model.addAttribute("ownBooks", books);

        return "people/show";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person")@Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
