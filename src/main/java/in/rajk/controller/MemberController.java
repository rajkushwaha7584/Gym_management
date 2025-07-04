package in.rajk.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import in.rajk.model.AppUser;
import in.rajk.model.DeletedMember;
import in.rajk.model.Member;
import in.rajk.repository.DeletedMemberRepository;
import in.rajk.repository.MemberRepository;
import in.rajk.repository.UserRepository;
import in.rajk.service.MailService;
import jakarta.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private DeletedMemberRepository deletedMemberRepository;           
//============================Registration=============================
    @PostMapping("/register")
    public String registerMember(@Valid @ModelAttribute("member") Member member,
                                 BindingResult result,
                                 Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        boolean isUpdate = (member.getId() != null);
        String rawPassword = member.getPassword();  // Can be null or blank
        String encodedPassword = null;

        // 🛡️ For new registration, check email/phone duplicates
        if (!isUpdate) {
            if (memberRepository.existsByEmail(member.getEmail())) {
                model.addAttribute("message", "❌ Email already registered. Please use a different one.");
                return "register";
            }
            if (memberRepository.existsByPhone(member.getPhone())) {
                model.addAttribute("message", "❌ Phone number already registered. Please use a different one.");
                return "register";
            }
        }

        boolean passwordChanged = false;

        if (isUpdate) {
            Member existing = memberRepository.findById(member.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

            // Preserve registration date
            member.setRegistrationDate(existing.getRegistrationDate());

            // Check if password is changed
            if (rawPassword == null || rawPassword.isBlank()) {
                member.setPassword(existing.getPassword());
            } else {
                encodedPassword = passwordEncoder.encode(rawPassword);
                member.setPassword(encodedPassword);
                passwordChanged = true;
            }
        } else {
            // New registration setup
            member.setRegistrationDate(LocalDate.now());
            encodedPassword = passwordEncoder.encode(rawPassword);
            member.setPassword(encodedPassword);
            passwordChanged = true;
        }

        // Set default profile picture
        if ("female".equalsIgnoreCase(member.getGender())) {
            member.setProfilePicture("default_female.png");
        } else {
            member.setProfilePicture("default_male.jpeg");
        }

        // Save member
        memberRepository.save(member);

        // ⚙️ Ensure AppUser record exists
        Optional<AppUser> existingUserOpt = userRepository.findByUsername(member.getEmail());
        if (existingUserOpt.isEmpty()) {
            AppUser user = new AppUser();
            user.setUsername(member.getEmail());
            user.setPassword(member.getPassword()); // already encoded
            user.setRole("ROLE_MEMBER");
            user.setEnabled(true);
            userRepository.save(user);

            // ✉️ Send welcome registration email
            mailService.sendCredentialsEmail(
                member.getEmail(),
                member.getFullName(),
                member.getEmail(),
                rawPassword,
                member
            );
        } else if (isUpdate) {
            // ✉️ Send update email (include password only if changed)
        	
            mailService.sendUpdateNotificationEmail(
                member.getEmail(),
                member.getFullName(),
                member,
                passwordChanged ? rawPassword : null
            );

            // 🔐 Update password in app_user table if changed
            if (passwordChanged) {
                AppUser user = existingUserOpt.get();
                user.setPassword(member.getPassword());
                userRepository.save(user);
            }
        }

        // 🎉 Message
        if (isUpdate) {
            model.addAttribute("message", "Member updated successfully!");
        } else {
            model.addAttribute("message", "Member registered successfully!");
            model.addAttribute("member", new Member()); // clear form
        }

        return "success";
    }
//==========================================================
    @GetMapping("/register")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showRegisterForm(Model model) {
        model.addAttribute("member", new Member());
        return "register";  // This returns the register.html form page
    }
//==========================================================

    @GetMapping("/members")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String listMembers(Model model) {
        model.addAttribute("members", memberRepository.findAll());
        return "members";
    }
//==========================================================
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        model.addAttribute("member", member);
        return "register";
    }
//==========================================================
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteMember(@PathVariable Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        DeletedMember deleted = new DeletedMember();

        // ✅ Set the ID manually
        deleted.setId(member.getId());

        deleted.setFullName(member.getFullName());
        deleted.setEmail(member.getEmail());
        deleted.setGender(member.getGender());
        deleted.setAge(member.getAge());
        deleted.setHeight(member.getHeight());
        deleted.setWeight(member.getWeight());
        deleted.setAddress(member.getAddress());
        deleted.setMobileNumber(member.getPhone());
        deleted.setRegistrationDate(member.getRegistrationDate());
        deleted.setMembershipDuration(member.getMembershipDuration());
        deleted.setAmountPaid(member.getAmountPaid());
        deleted.setDeletedOn(LocalDate.now());

        deletedMemberRepository.save(deleted);

        userRepository.findByUsername(member.getEmail()).ifPresent(userRepository::delete);
        memberRepository.delete(member);

        return "redirect:/members";
    }
//==========================================================
    @GetMapping("/deleted-members")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String viewDeletedMembers(Model model) {
        model.addAttribute("deletedMembers", deletedMemberRepository.findAll());
        return "deleted_members";
    }
//==========================================================
    @GetMapping("/members/details/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public String showMemberDetails(@PathVariable Long id, Model model, Principal principal) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        String currentEmail = principal.getName();

        if (!currentEmail.equalsIgnoreCase(member.getEmail())) {
            Optional<AppUser> currentUser = userRepository.findByUsername(currentEmail);
            if (currentUser.isPresent() && !currentUser.get().getRole().equals("ROLE_ADMIN")) {
                return "error/403";
            }
        }

        LocalDate expiryDate = member.getRegistrationDate().plusMonths(member.getMembershipDuration());
        long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);

        model.addAttribute("member", member);
        model.addAttribute("expiryDate", expiryDate);
        model.addAttribute("daysLeft", daysLeft);
        return "details";
    }
//====================================
    @GetMapping("/sync-members")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String syncOldMembersToUsers(Model model) {
        List<Member> members = memberRepository.findAll();
        int added = 0;

        for (Member m : members) {
            if (!userRepository.findByUsername(m.getEmail()).isPresent()) {
                AppUser u = new AppUser();
                u.setUsername(m.getEmail());
                u.setPassword(m.getPassword());
                u.setRole("ROLE_MEMBER");
                u.setEnabled(true);
                userRepository.save(u);
                added++;
            }
        }

        model.addAttribute("message", added + " members synced to app_user table.");
        return "members";
    }
    
    //===
    @GetMapping("/members/expiring-soon")
    public String getExpiringSoonMembers(Model model) {
        LocalDate today = LocalDate.now();
        LocalDate cutoff = today.plusDays(7);
        List<Member> expiring = memberRepository.findByExpiryDateBetween(today, cutoff);
        model.addAttribute("members", expiring);
        model.addAttribute("viewTitle", "🕒 Members Expiring Soon");
        return "members"; // reuse same Thymeleaf table
    }

    @GetMapping("/members/expired")
    public String getExpiredMembers(Model model) {
        LocalDate today = LocalDate.now();
        List<Member> expired = memberRepository.findByExpiryDateBefore(today);
        model.addAttribute("members", expired);
        model.addAttribute("viewTitle", "🚫 Expired Members");
        return "members"; // reuse same Thymeleaf table
    }

}
