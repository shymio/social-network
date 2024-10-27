package pet.social_network_user_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.social_network_user_service.service.JwtService;
import pet.social_network_user_service.service.SubscriptionService;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final JwtService jwtService;

    @PostMapping("/follow/{followingId}")
    public ResponseEntity<String> follow(@PathVariable Long followingId, @RequestHeader("Authorization") String token) {
        Optional<Long> userId = jwtService.extractUserId(token);
        if (userId.isPresent()) {
            subscriptionService.follow(userId.get(), followingId);
            log.info("User {} is followed to user {}", userId.get(), followingId);
            return ResponseEntity.ok("Following was successful");
        } else {
            log.info("UserId is not extracted (follow)");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
