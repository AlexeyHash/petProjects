package ru.alexey.spring.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alexey.spring.library.dao.BooksDAO;
import ru.alexey.spring.library.dao.PersonDAO;
import ru.alexey.spring.library.model.Book;
import ru.alexey.spring.library.model.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksDAO booksDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BooksDAO booksDAO, PersonDAO personDAO) {
        this.booksDAO = booksDAO;
        this.personDAO = personDAO;
    }
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", booksDAO.index());

        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", booksDAO.show(id));
        Optional<Person> owner = booksDAO.getOwner(id);
        if (owner.isPresent()){
            model.addAttribute("owner", owner.get());
        }
        else {
            model.addAttribute("people", personDAO.index());
        }

        return "books/show";
    }
    @PatchMapping("/{id}/appoint")
    public String appointBook(@PathVariable("id")int id,
                              @ModelAttribute("person") Person person){
        booksDAO.appointBook(id, person);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id")int id){
        booksDAO.release(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", booksDAO.show(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("id") int id){
        booksDAO.update(id, book);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book")Book book){
        booksDAO.save(book);
        return "redirect:/books";
    }
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id){
        booksDAO.delete(id);
        return "redirect:/books";
    }
}
