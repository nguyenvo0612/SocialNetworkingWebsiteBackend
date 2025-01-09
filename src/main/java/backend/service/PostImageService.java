package backend.service;

import backend.entity.PostImage;
import backend.repository.PostImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostImageService {
    @Autowired
    private PostImageRepository postImageRepository;

    public List<PostImage> getAllPostImages() {
        return postImageRepository.findAll();
    }

    public PostImage deletePostImage(Long postImageId) {
        return postImageRepository.deletePostImageByImageId(postImageId);
    }
}
