package be.bookstore.service;

import be.bookstore.model.Book;
import be.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BookService {
    private final BookRepository books;
    public BookService(BookRepository books) { this.books = books; }

    public List<Book> findAll() { return books.findAll(); }
    public Book findById(Long id) {
        return books.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }
    @Transactional
    public Book create(Book book) {
        if (books.findByIsbn(book.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("ISBN already exists");
        }
        return books.save(book);
    }
}
