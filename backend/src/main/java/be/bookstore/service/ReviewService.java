package be.bookstore.service;

import be.bookstore.model.AppUser;
import be.bookstore.model.Book;
import be.bookstore.model.Review;
import be.bookstore.repository.BookRepository;
import be.bookstore.repository.ReviewRepository;
import be.bookstore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ReviewService {
    private final BookRepository books;
    private final UserRepository users;
    private final ReviewRepository reviews;

    public ReviewService(BookRepository books, UserRepository users, ReviewRepository reviews) {
        this.books = books;
        this.users = users;
        this.reviews = reviews;
    }

    public List<Review> findForBook(Long bookId) {
        if (!books.existsById(bookId)) throw new IllegalArgumentException("Book not found");
        return reviews.findByBookIdOrderByCreatedAtDesc(bookId);
    }

    @Transactional
    public Review create(Long bookId, Long userId, Integer score, String text) {
        if (score == null || score < 1 || score > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }
        if (text == null || text.isBlank()) throw new IllegalArgumentException("Review text is required");
        Book book = books.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        AppUser user = users.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return reviews.save(new Review(book, user, score, text.trim()));
    }
}
