package in.rajk.controller;

import in.rajk.model.Member;
import in.rajk.repository.GalleryImageRepository;
import in.rajk.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GalleryImageRepository galleryRepo;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();

            Optional<Member> member = memberRepository.findByEmail(email);
            if (member.isPresent()) {
                model.addAttribute("userType", "member");
                model.addAttribute("userName", member.get().getFullName());
            } else {
                model.addAttribute("userType", "admin");
                model.addAttribute("userName", "Admin");
            }
        }

        // 🔽 Add these lines
        model.addAttribute("trainerImages", galleryRepo.findByCategory("trainer"));
        model.addAttribute("memberImages", galleryRepo.findByCategory("member"));
        model.addAttribute("transformationImages", galleryRepo.findByCategory("transformation"));

        return "index";
    }


    @GetMapping("/gallery/trainers")
    public String trainers() {
        return "trainers";
    }

    @GetMapping("/gallery/transformations")
    public String transformations() {
        return "transformations";
    }

    @GetMapping("/trainerbio")
    public String trainerBio() {
        return "trainerbio";
    }
    
}
