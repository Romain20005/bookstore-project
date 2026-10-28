package be.bookstore.service;

import be.bookstore.model.AppUser;
import be.bookstore.model.Book;
import be.bookstore.model.Reservation;
import be.bookstore.repository.BookRepository;
import be.bookstore.repository.ReservationRepository;
import be.bookstore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ReservationService {
    private final BookRepository books;
    private final UserRepository users;
    private final ReservationRepository reservations;

    public ReservationService(BookRepository books, UserRepository users, ReservationRepository reservations) {
        this.books = books;
        this.users = users;
        this.reservations = reservations;
    }

    @Transactional
    public Reservation reserve(Long userId, Long bookId, int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be at least 1");

        AppUser user = users.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Book book = books.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (book.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        book.setStock(book.getStock() - quantity);
        books.save(book);

        return reservations.save(new Reservation(user, book, quantity));
    }

    public List<Reservation> findForUser(Long userId) {
        if (!users.existsById(userId)) throw new IllegalArgumentException("User not found");
        return reservations.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
