package in.rajk.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.rajk.model.Member;
import in.rajk.repository.DeletedMemberRepository;
import in.rajk.repository.MemberRepository;

@Controller
public class AuthController {
	@Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private DeletedMemberRepository DeletedMemberRepository;

    public AuthController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // returns login.html
    }
    
    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // this will load 403.html
    }
    
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    @GetMapping("/members/dashboard")
    public String memberDashboard(Model model, Principal principal) {
        String email = principal.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        model.addAttribute("member", member);
        return "member_dashboard";
    }
        
    //=======admin-dashboard===========
    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {
        long totalMembers = memberRepository.countAllMembers(); // your existing logic
        long totalDeletedMembers = DeletedMemberRepository.count(); // add this line

        model.addAttribute("totalMembers", totalMembers);
        model.addAttribute("totalDeletedMembers", totalDeletedMembers); // add this line too

        System.out.println("Total members: " + totalMembers);
        System.out.println("Total deleted members: " + totalDeletedMembers);

        return "admin_dashboard";
    }

}
