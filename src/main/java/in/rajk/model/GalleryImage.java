package in.rajk.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String title;

    @Column(length = 255)
    private String category; // e.g., "trainer", "transformation", "member"

    private String uploadDate;

    // Optional: for captions
    private String description;
    private String caption;

	public GalleryImage() {
		super();
	}

	public GalleryImage(Long id, String filename, String title, String category, String uploadDate, String description,
			String caption) {
		super();
		this.id = id;
		this.filename = filename;
		this.title = title;
		this.category = category;
		this.uploadDate = uploadDate;
		this.description = description;
		this.caption = caption;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Override
	public String toString() {
		return "GalleryImage [id=" + id + ", filename=" + filename + ", title=" + title + ", category=" + category
				+ ", uploadDate=" + uploadDate + ", description=" + description + ", caption=" + caption + "]";
	}


}
