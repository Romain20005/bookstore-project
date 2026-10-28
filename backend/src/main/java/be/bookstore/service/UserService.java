package be.bookstore.service;

import be.bookstore.model.AppUser;
import be.bookstore.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository users;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository users) { this.users = users; }

    @Transactional
    public AppUser register(String name, String email, String password) {
        if (users.findByEmailIgnoreCase(email).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }
        return users.save(new AppUser(name, email.toLowerCase(), encoder.encode(password)));
    }

    public AppUser login(String email, String password) {
        AppUser user = users.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!encoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return user;
    }
}
