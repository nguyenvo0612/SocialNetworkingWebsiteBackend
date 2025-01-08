package backend.controller;

import backend.entity.Post;
import backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/getAll")
    public List<Post> getAll() {
        return postService.getAllPosts();
    }

    @GetMapping("/find/{postId}")
    public Post findbyId(@RequestParam Long postId) {
        return postService.findPostById(postId);
    }

    @DeleteMapping("/delete/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePostById(postId);
    }
}
