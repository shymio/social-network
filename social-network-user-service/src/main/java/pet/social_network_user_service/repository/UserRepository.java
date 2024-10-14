package pet.social_network_user_service.repository;

import org.springframework.data.repository.CrudRepository;
import pet.social_network_user_service.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

}
