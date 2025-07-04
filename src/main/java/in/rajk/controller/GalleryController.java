package in.rajk.controller;

import in.rajk.model.GalleryImage;
import in.rajk.repository.GalleryImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

@Controller
public class GalleryController {

    private final Path uploadDir;

    private final GalleryImageRepository galleryRepo;

    public GalleryController(GalleryImageRepository galleryRepo,
                             @Value("${gallery.upload-dir:uploads/gallery}") String dir) throws IOException {
        this.galleryRepo = galleryRepo;
        this.uploadDir = Paths.get(dir);
        Files.createDirectories(uploadDir);
    }

    @GetMapping("/admin/gallery")
    public String showGalleryPage(Model model) {
        List<GalleryImage> allImages = galleryRepo.findAll();
        model.addAttribute("images", allImages);
        return "admin/gallery";
    }

    @PostMapping("/admin/gallery/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file,
                              @RequestParam("title") String title,
                              @RequestParam("category") String category,
                              @RequestParam(value = "description", required = false) String desc) throws IOException {
        if (!file.isEmpty()) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path destination = uploadDir.resolve(filename);
            file.transferTo(destination);

            GalleryImage image = new GalleryImage();
            image.setFilename(filename);
            image.setTitle(title);
            image.setCategory(category);
            image.setDescription(desc);
            image.setUploadDate(LocalDate.now().toString());

            galleryRepo.save(image);
        }
        return "redirect:/admin/gallery";
    }

    @GetMapping("/gallery/image/{filename}")
    @ResponseBody
    public Resource getImage(@PathVariable String filename) throws MalformedURLException {
        Path filePath = uploadDir.resolve(filename);
        return new UrlResource(filePath.toUri());
    }

    @GetMapping("/admin/gallery/delete/{id}")
    public String deleteImage(@PathVariable Long id) throws IOException {
        GalleryImage image = galleryRepo.findById(id).orElse(null);
        if (image != null) {
            Files.deleteIfExists(uploadDir.resolve(image.getFilename()));
            galleryRepo.deleteById(id);
        }
        return "redirect:/admin/gallery";
    }
    @GetMapping("/public/gallery")
    public String showPublicGallery(Model model) {
        model.addAttribute("trainerImages", galleryRepo.findByCategory("trainer"));
        model.addAttribute("transformationImages", galleryRepo.findByCategory("transformation"));
        model.addAttribute("memberImages", galleryRepo.findByCategory("member"));
        return "index";
    }
    @GetMapping("/gallery")
    public String publicGalleryPage(Model model) {
        model.addAttribute("trainerImages", galleryRepo.findByCategory("trainer"));
        model.addAttribute("transformationImages", galleryRepo.findByCategory("transformation"));
        model.addAttribute("memberImages", galleryRepo.findByCategory("member"));
        return "gallery";  // gallery.html in templates
    }

}
