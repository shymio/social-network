package pet.social_network_user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.social_network_user_service.entity.Subscription;
import pet.social_network_user_service.repository.SubscriptionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public void follow(Long followerId, Long followingId) {
        Subscription subscription = Subscription.builder()
                .followingId(followingId)
                .followerId(followerId)
                .build();

        subscriptionRepository.save(subscription);

    }

    public void unfollow(Long followerId, Long followingId) {
        Optional<Subscription> subscription = subscriptionRepository.findByFollowerIdAndFollowingId(followerId, followingId);
        subscription.ifPresent(subscriptionRepository::delete);
    }

}
