package be.bookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Book book;

    @ManyToOne(optional = false)
@JsonIgnoreProperties({"passwordHash"})
private AppUser user;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer score;

    @NotBlank
    @Column(nullable = false, length = 2000)
    private String text;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Review() {}
    public Review(Book book, AppUser user, Integer score, String text) {
        this.book = book;
        this.user = user;
        this.score = score;
        this.text = text;
        this.createdAt = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public Book getBook() { return book; }
    public AppUser getUser() { return user; }
    public Integer getScore() { return score; }
    public String getText() { return text; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
