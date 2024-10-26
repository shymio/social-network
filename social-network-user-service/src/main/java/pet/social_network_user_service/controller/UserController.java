package pet.social_network_user_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pet.social_network_user_service.service.UserService;
import pet.social_network_user_service.entity.User;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;
//    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/me")
    public ResponseEntity<Optional<User>> authenticatedUser(@RequestHeader("X-Authenticated-User") String username) {
        log.info("Authenticated user endpoint called");
        log.info("Username: {}", username);

        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        Optional<User> user = userService.findByEmail(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users =  userService.allUsers();
        return ResponseEntity.ok(users);
    }
}
