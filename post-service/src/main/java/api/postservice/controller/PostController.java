package api.postservice.controller;

import api.postservice.dto.PostRequest;
import api.postservice.entity.Post;
import api.postservice.service.JwtService;
import api.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(@RequestHeader("Authorization") String token) {
        Optional<Long> userId = jwtService.extractUserId(token);
        if (userId.isPresent()) {
            log.info("UserId for all pasts of user: {}", userId.get());
            List<Post> posts = postService.findAll(userId.get());
            if (posts.isEmpty()) {
                log.info("Posts not found for user: {}", userId.get());
                return ResponseEntity.notFound().build();
            } else
                return ResponseEntity.ok(posts);
        } else {
            log.info("UserId is not extracted (getAllPosts)");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest, @RequestHeader("Authorization") String token) {
        Optional<Long> userId = jwtService.extractUserId(token);
        if (userId.isPresent()) {
            log.info("UserId for create post: {}", userId);
            Post newPost = postService.save(postRequest, userId.get());
            log.info("New post is created: {}", newPost.getTitle());
            return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
        } else {
            log.info("UserId is not extracted (create)");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Post>> getPost(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            return ResponseEntity.ok(post);
        }
        return ResponseEntity.notFound().build();
    }


}
