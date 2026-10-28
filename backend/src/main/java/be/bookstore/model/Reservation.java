package be.bookstore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private AppUser user;

    @ManyToOne(optional = false)
    private Book book;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String status = "READY_FOR_PICKUP";

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Reservation() {}
    public Reservation(AppUser user, Book book, Integer quantity) {
        this.user = user;
        this.book = book;
        this.quantity = quantity;
        this.status = "READY_FOR_PICKUP";
        this.createdAt = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public AppUser getUser() { return user; }
    public Book getBook() { return book; }
    public Integer getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
