package pet.social_network_user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.social_network_user_service.entity.User;
import pet.social_network_user_service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public Optional<User> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }
}
