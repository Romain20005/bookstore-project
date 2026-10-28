package be.bookstore.service;

import be.bookstore.model.AppUser;
import be.bookstore.model.Book;
import be.bookstore.model.Reservation;
import be.bookstore.repository.BookRepository;
import be.bookstore.repository.ReservationRepository;
import be.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock BookRepository books;
    @Mock UserRepository users;
    @Mock ReservationRepository reservations;
    @InjectMocks ReservationService service;

    private AppUser user;
    private Book book;

    @BeforeEach
    void setup() {
        user = new AppUser("Test User", "test@example.com", "hash");
        book = new Book("9780000000001", "Test Book", "Test Author", "Description", new BigDecimal("10.00"), 3);
    }

    @Test
    void reservationDecreasesStock() {
        when(users.findById(1L)).thenReturn(Optional.of(user));
        when(books.findById(2L)).thenReturn(Optional.of(book));
        when(reservations.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));

        service.reserve(1L, 2L, 2);

        assertEquals(1, book.getStock());
        verify(books).save(book);
        verify(reservations).save(any(Reservation.class));
    }

    @Test
    void reservationFailsWhenStockIsInsufficient() {
        when(users.findById(1L)).thenReturn(Optional.of(user));
        when(books.findById(2L)).thenReturn(Optional.of(book));

        assertThrows(IllegalArgumentException.class, () -> service.reserve(1L, 2L, 4));

        assertEquals(3, book.getStock());
        verify(reservations, never()).save(any());
    }

    @Test
    void reservationRejectsZeroOrNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> service.reserve(1L, 2L, 0));
        assertThrows(IllegalArgumentException.class, () -> service.reserve(1L, 2L, -1));
        verifyNoInteractions(books, users, reservations);
    }
}
