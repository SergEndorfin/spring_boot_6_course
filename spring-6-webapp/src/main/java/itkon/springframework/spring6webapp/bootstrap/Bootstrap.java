package itkon.springframework.spring6webapp.bootstrap;

import itkon.springframework.spring6webapp.domain.Author;
import itkon.springframework.spring6webapp.domain.Book;
import itkon.springframework.spring6webapp.domain.Publisher;
import itkon.springframework.spring6webapp.repositories.AuthorRepository;
import itkon.springframework.spring6webapp.repositories.BookRepository;
import itkon.springframework.spring6webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public Bootstrap(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");

        Book book1 = new Book();
        book1.setTitle("Test title");
        book1.setIsbn("111111");

        var ericSaved = authorRepository.save(eric);
        var book1Saved = bookRepository.save(book1);

        Author rob = new Author();
        rob.setFirstName("Rob");
        rob.setLastName("Johnson");

        Book book2 = new Book();
        book2.setTitle("Test 22222");
        book2.setIsbn("222222");

        var robSaved = authorRepository.save(rob);
        var book2Saved = bookRepository.save(book2);

        ericSaved.getBooks().add(book1Saved);
        robSaved.getBooks().add(book2Saved);
        book1Saved.getAuthors().add(ericSaved);
        book2Saved.getAuthors().add(robSaved);

        Publisher publisher = new Publisher();
        publisher.setPublisherName("Pub_name");
        publisher.setAddress("123 Main");
        Publisher publisherSaved = publisherRepository.save(publisher);

        book1Saved.setPublisher(publisherSaved);
        book2Saved.setPublisher(publisherSaved);

        authorRepository.save(ericSaved);
        authorRepository.save(robSaved);
        bookRepository.save(book1Saved);
        bookRepository.save(book2Saved);

        System.out.println("In Bootstrap");
        System.out.println("Author count: " + authorRepository.count());
        System.out.println("Book count: " + bookRepository.count());
        System.out.println("Publisher count: " + publisherRepository.count());
    }
}
