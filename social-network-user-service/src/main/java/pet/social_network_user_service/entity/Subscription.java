package pet.social_network_user_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "follower_id")
    private Long followerId;
    @Column(nullable = false, name = "following_id")
    private Long followingId;
}
