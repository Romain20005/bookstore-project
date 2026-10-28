package be.bookstore.api;

import be.bookstore.model.Review;
import be.bookstore.service.ReviewService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books/{bookId}/reviews")
public class ReviewController {
    private final ReviewService service;
    public ReviewController(ReviewService service) { this.service = service; }

    public record ReviewRequest(@NotNull Long userId, @NotNull @Min(1) @Max(5) Integer score, @NotBlank String text) {}

    @GetMapping
    public List<Review> all(@PathVariable Long bookId) { return service.findForBook(bookId); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review create(@PathVariable Long bookId, @Valid @RequestBody ReviewRequest request) {
        return service.create(bookId, request.userId(), request.score(), request.text());
    }
}
