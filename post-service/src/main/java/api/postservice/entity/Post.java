package api.postservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Table(name = "posts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String title;
    private String description;
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
    @Column(name = "user_id")
    private Long author;


    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
