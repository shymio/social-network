package api.postservice.service;

import api.postservice.dto.PostRequest;
import api.postservice.entity.Post;
import api.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> findAll(Long userId) {
        return postRepository.findAllById(userId);
    }

    public Post save(PostRequest postRequest, Long userId) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setAuthor(userId);
        return postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }


}
