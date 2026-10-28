package be.bookstore.api;

import be.bookstore.model.Reservation;
import be.bookstore.service.ReservationService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController {
    private final ReservationService service;
    public ReservationController(ReservationService service) { this.service = service; }

    public record ReservationRequest(@NotNull Long userId, @NotNull Long bookId, @Min(1) int quantity) {}

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation reserve(@Valid @RequestBody ReservationRequest request) {
        return service.reserve(request.userId(), request.bookId(), request.quantity());
    }

    @GetMapping("/users/{userId}/reservations")
    public List<Reservation> forUser(@PathVariable Long userId) { return service.findForUser(userId); }
}
