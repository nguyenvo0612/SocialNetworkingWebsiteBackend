package backend.service;

import backend.entity.Post;
import backend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post findPostById(Long id) {
        return postRepository.findPostByPostId(id);
    }

    public Post deletePostById(Long id) {
        Post post = postRepository.findPostByPostId(id);
        postRepository.delete(post);
        return post;
    }
}
