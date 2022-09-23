package alexey.springJPA.library.repositories;

import alexey.springJPA.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitleStartingWith(String title);



}
