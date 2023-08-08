package itkon.springframework.spring6webapp.services;

import itkon.springframework.spring6webapp.domain.Book;

public interface BookService {

    Iterable<Book> findAll();
}
