package itkon.springframework.spring6webapp.repositories;

import itkon.springframework.spring6webapp.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
