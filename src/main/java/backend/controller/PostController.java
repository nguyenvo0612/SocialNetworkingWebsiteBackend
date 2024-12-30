package backend.controller;

import backend.entity.Post;
import backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
