package backend.controller;

import backend.entity.PostImage;
import backend.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/post-image")
public class PostImageController {
    @Autowired
    private PostImageService postImageService;

    @GetMapping
    public List<PostImage> getPostImages() {
        return postImageService.getAllPostImages();
    }
}
