package backend.repository;

import backend.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    PostImage deletePostImageByImageId(Long imageId);
}
