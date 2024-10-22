package api.postservice.controller;

import api.postservice.entity.Post;
import api.postservice.service.JwtService;
import api.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Post>>  getAllPosts(@RequestHeader("Authorization") String token) {
        Long userId = jwtService.extractUserId(token);
        List<Post> posts = postService.findAll(userId);
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        } return ResponseEntity.ok(posts);
    }

    @PostMapping("/create")
    public Post createPost(@RequestBody Post post) {
        Post newpost = post;
        return postService.save(newpost);
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
