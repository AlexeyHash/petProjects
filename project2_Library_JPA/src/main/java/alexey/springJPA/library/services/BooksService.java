package alexey.springJPA.library.services;

import alexey.springJPA.library.models.Book;
import alexey.springJPA.library.models.Person;
import alexey.springJPA.library.repositories.BooksRepository;
import alexey.springJPA.library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BooksService {
    private final BooksRepository booksRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findAllSortByYear(){
        return booksRepository.findAll(Sort.by("year"));
    }

    public List<Book> findAllPaginationAndSort(Integer page, Integer size){


        return booksRepository.findAll(PageRequest.of(page, size, Sort.by("year"))).getContent();
    }

    public List<Book> findAllPagination(int page, int size){

        return booksRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Book findOne(int id){
        return booksRepository.findById(id).orElse(null);
    }
    public void save(Book book){
        booksRepository.save(book);
    }
    public void update(int id, Book book){
        Book bookToBeUpdated = booksRepository.findById(id).get();
        book.setId(id);
        book.setOwner(bookToBeUpdated.getOwner());
        booksRepository.save(book);
    }

    public void delete(int id){
        booksRepository.deleteById(id);
    }


    public void appointBook(int id, Person person){
        booksRepository.findById(id).ifPresent(
                book -> {
            book.setOwner(person);
            book.setTakenDate(new Date());
        });
    }

    public void release(int id){
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenDate(null);
                });
    }

    public Optional<Person> getOwner(int id){
        Book book = booksRepository.findById(id).orElse(null);
        if (book.getOwner() != null){
            return Optional.of(book.getOwner());
        }
        return Optional.empty();
    }

    public List<Book> findBook(String title){
        return booksRepository.findByTitleStartingWith(title);
    }

}
