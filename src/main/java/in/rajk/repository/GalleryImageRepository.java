package in.rajk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import in.rajk.model.GalleryImage;

public interface GalleryImageRepository extends JpaRepository<GalleryImage, Long> {
    List<GalleryImage> findByCategory(String category);
}
