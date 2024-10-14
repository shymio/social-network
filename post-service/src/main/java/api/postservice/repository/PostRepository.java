package api.postservice.repository;

import api.postservice.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Post findByTitle(String title);

    List<Post> findAll(Long userId);

}
