package pet.social_network_user_service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pet.social_network_user_service.entity.Subscription;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    Optional<Subscription> findByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
