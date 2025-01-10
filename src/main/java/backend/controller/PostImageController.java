package backend.controller;

import backend.entity.PostImage;
import backend.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post-image")
public class PostImageController {
    @Autowired
    private PostImageService postImageService;

    @GetMapping("/getAll")
    public List<PostImage> getPostImages() {
        return postImageService.getAllPostImages();
    }

    @DeleteMapping("/delete/{imageId}")
    public PostImage deletePostImage(@PathVariable Long imageId) {
        return postImageService.deletePostImage(imageId);
    }

}
