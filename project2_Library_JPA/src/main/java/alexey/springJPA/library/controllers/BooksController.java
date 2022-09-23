package alexey.springJPA.library.controllers;

import alexey.springJPA.library.models.Book;
import alexey.springJPA.library.models.Person;
import alexey.springJPA.library.services.BooksService;
import alexey.springJPA.library.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }
    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page",required = false) Integer page,
                        @RequestParam(value = "books_per_page",required = false) Integer size,
                        @RequestParam(value = "sort_by_year", required = false) boolean sort) {

         if (page == null
                && size == null
                && !sort){
            model.addAttribute("books", booksService.findAll());
        } else if (page == null
                && size == null
                && sort){
            model.addAttribute("books", booksService.findAllSortByYear());
        } else if (page >=0
                 && size >0
                 && !sort){
             model.addAttribute("books", booksService.findAllPagination(page,size));
         }
        else
            model.addAttribute("books", booksService.findAllPaginationAndSort(page, size));

        return "books/index";
    }
    @GetMapping("/{id}")
    public Book show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){

        model.addAttribute("book", booksService.findOne(id));
        Optional<Person> owner = booksService.getOwner(id);
        if (owner.isPresent()){
            model.addAttribute("owner", owner.get());
        }
        else {
            model.addAttribute("people", peopleService.findAll());
        }

        return booksService.findOne(id);
    }


    @PatchMapping("/{id}/appoint")
    public String appointBook(@PathVariable("id")int id,
                              @ModelAttribute("person") Person person){
        booksService.appointBook(id, person);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id")int id){
        booksService.release(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("id") int id){
        booksService.update(id, book);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(){
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(@RequestParam(value = "title") String title, Model model){
         model.addAttribute("books", booksService.findBook(title));
        return "books/search";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book")Book book){
        booksService.save(book);
        return "redirect:/books";
    }
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }
}
