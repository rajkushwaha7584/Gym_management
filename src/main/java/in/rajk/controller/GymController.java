//package in.rajk.controller;
//
//import java.io.IOException;
//import java.security.Principal;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import in.rajk.model.AppUser;
//import in.rajk.model.BmiRequest;
//import in.rajk.model.Member;
//import in.rajk.repository.MemberRepository;
//import in.rajk.service.ExcelExporter;
//import in.rajk.service.WhatsAppService;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.Valid; // ✅ Required for validation
//
//@Controller
//public class GymController {
//	@Autowired
//	private WhatsAppService whatsAppService;
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
//
//    @Controller
//    public class HomeController {
//
//        @GetMapping("/")
//        public String home() {
//            return "index";
//        }
//
//        @GetMapping("/gallery/trainers")
//        public String trainers() {
//            return "trainers"; // Create trainers.html if needed
//        }
//
//        @GetMapping("/gallery/transformations")
//        public String transformations() {
//            return "transformations"; // Create transformations.html if needed
//        }
//
//        // ✅ Add this method for trainerbio page
//        @GetMapping("/trainerbio")
//        public String trainerBio() {
//            return "trainerbio"; // This loads templates/trainerbio.html
//        }
//    }
//    @GetMapping("/sync-members")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String syncOldMembersToUsers(Model model) {
//        List<Member> members = memberRepository.findAll();
//        int added = 0;
//
//        for (Member m : members) {
//            if (!userRepository.findByUsername(m.getEmail()).isPresent()) {
//                AppUser u = new AppUser();
//                u.setUsername(m.getEmail());
//                u.setPassword(m.getPassword()); // In production, hash this!
//                u.setRole("ROLE_MEMBER");
//                u.setEnabled(true);
//                userRepository.save(u);
//                added++;
//            }
//        }
//
//        model.addAttribute("message", added + " members synced to app_user table.");
//        return "members";
//    }
//
//
//    
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("member", new Member());
//        return "register";
//    }
//
// // Show registration form
//    @PostMapping("/register")
//    public String registerMember(@Valid @ModelAttribute("member") Member member,
//                                 BindingResult result,
//                                 Model model) {
//
//        if (result.hasErrors()) {
//            return "register";
//        }
//
//        boolean isUpdate = (member.getId() != null);
//        String encodedPassword = null;
//
//        if (isUpdate) {
//            Member existing = memberRepository.findById(member.getId())
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
//
//            // Preserve registration date
//            member.setRegistrationDate(existing.getRegistrationDate());
//
//            // Preserve password if not updated
//            if (member.getPassword() == null || member.getPassword().isBlank()) {
//                member.setPassword(existing.getPassword());
//            } else {
//                // ✅ Encode the new password
//                encodedPassword = passwordEncoder.encode(member.getPassword());
//                member.setPassword(encodedPassword);
//            }
//        } else {
//            member.setRegistrationDate(LocalDate.now());
//
//            // ✅ Encode password for new registration
//            encodedPassword = passwordEncoder.encode(member.getPassword());
//            member.setPassword(encodedPassword);
//        }
//
//        // ✅ Default profile picture
//        if ("female".equalsIgnoreCase(member.getGender())) {
//            member.setProfilePicture("default_female.png");
//        } else {
//            member.setProfilePicture("default_male.jpeg");
//        }
//
//        // ✅ Save member
//        memberRepository.save(member);
//
//        // 🔐 Save AppUser only if not exists
//        if (!userRepository.existsByUsername(member.getEmail())) {
//            AppUser user = new AppUser();
//            user.setUsername(member.getEmail());
//            user.setPassword(member.getPassword()); // ✅ Already encoded
//            user.setRole("ROLE_MEMBER");
//            user.setEnabled(true);
//            userRepository.save(user);
//        }
//
//        // ✅ Success message
//        if (isUpdate) {
//            model.addAttribute("message", "Member updated successfully!");
//        } else {
//            model.addAttribute("message", "Member registered successfully!");
//            model.addAttribute("member", new Member()); // Reset form
//        }
//
//        return "success";
//    }
//
//
//    // List all members
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/members")
//    public String listMembers(Model model) {
//        model.addAttribute("members", memberRepository.findAll());
//        return "members"; // members.html
//    }
//    
//    //delet 
//    @GetMapping("/delete/{id}")
//    public String deleteMember(@PathVariable Long id) {
//        memberRepository.deleteById(id);
//        return "redirect:/members"; // Refresh member list after deletion
//    }
//
//    //edit
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
//        model.addAttribute("member", member);
//        return "register"; // reuse the same form
//    }
//    
//    //Add Search Handler
//    @GetMapping("/members/search")
//    public String searchMembers(@org.springframework.web.bind.annotation.RequestParam("keyword") String keyword, Model model) {
//        model.addAttribute("members", memberRepository.searchByNameOrPhone(keyword));
//        model.addAttribute("keyword", keyword); // so the input retains value
//        return "members";
//    }
//    //members details
////    @GetMapping("/members/details/{id}")
////    public String showMemberDetails(@PathVariable Long id, Model model) {
////        Member member = memberRepository.findById(id)
////                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));
////
////        LocalDate expiryDate = member.getRegistrationDate().plusMonths(member.getMembershipDuration());
////        long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
////
////        model.addAttribute("member", member);
////        model.addAttribute("expiryDate", expiryDate);
////        model.addAttribute("daysLeft", daysLeft);
////
////        return "details"; // details.html
////    }
//    
//    //member details
//    @GetMapping("/members/details/{id}")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
//    public String showMemberDetails(@PathVariable Long id, Model model, Principal principal) {
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
//
//        String currentEmail = principal.getName();
//
//        // Allow if ADMIN or the same MEMBER
//        if (!currentEmail.equalsIgnoreCase(member.getEmail())) {
//            Optional<AppUser> currentUser = userRepository.findByUsername(currentEmail);
//            if (currentUser.isPresent() && !currentUser.get().getRole().equals("ROLE_ADMIN")) {
//                return "error/403";
//            }
//        }
//
//        LocalDate expiryDate = member.getRegistrationDate().plusMonths(member.getMembershipDuration());
//        long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
//
//        model.addAttribute("member", member);
//        model.addAttribute("expiryDate", expiryDate);
//        model.addAttribute("daysLeft", daysLeft);
//        return "details";
//    }
//    @Autowired
//    private in.rajk.repository.UserRepository userRepository; // Inject your AppUser repo
//    private String getLoggedInUserRole(String email) {
//        return userRepository.findByUsername(email)
//                .map(u -> u.getRole() != null ? u.getRole() : "")
//                .orElse("");
//    }
//
//
////excel export==========================================
//    @GetMapping("/export")
//    @PreAuthorize("hasRole('ADMIN')")
//    public void exportToExcel(HttpServletResponse response) throws IOException {
//        response.setContentType("application/octet-stream");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=gym_members.xlsx";
//        response.setHeader(headerKey, headerValue);
//
//        List<Member> listMembers = memberRepository.findAll();
//        ExcelExporter excelExporter = new ExcelExporter(listMembers);
//        excelExporter.export(response);
//    }
//
//    //whatsApp Notification=========================================
// // ✅ WhatsApp Expiry Reminder (Improved)
//    @GetMapping("/send-reminders")
//    public String sendExpiryReminders(Model model) {
//        LocalDate today = LocalDate.now();
//        List<Member> members = memberRepository.findAll();
//        int sentCount = 0;
//
//        for (Member m : members) {
//            LocalDate expiryDate = m.getRegistrationDate().plusMonths(m.getMembershipDuration());
//            String phone = m.getPhone().trim();
//
//            // ✅ Normalize phone to international format
//            if (!phone.startsWith("+")) {
//                if (phone.startsWith("0")) {
//                    phone = phone.substring(1);
//                }
//                phone = "+91" + phone;
//            }
//
//            try {
//                // ⏳ Send "3 days left" reminder
//                if (expiryDate.minusDays(3).isEqual(today)) {
//                    String msg = "Hi " + m.getFullName() +
//                            ", your gym membership will expire on " + expiryDate +
//                            ". Please renew it soon.";
//                    whatsAppService.sendMessage(phone, msg);
//                    sentCount++;
//                    System.out.println("⏳ Reminder sent to: " + phone);
//                }
//
//                // ❌ Send "already expired" notice
//                else if (expiryDate.isBefore(today)) {
//                    String msg = "Hi " + m.getFullName() +
//                            ", your gym membership expired on " + expiryDate +
//                            ". Please renew it to continue your fitness journey 💪";
//                    whatsAppService.sendMessage(phone, msg);
//                    sentCount++;
//                    System.out.println("❌ Expiry alert sent to: " + phone);
//                }
//
//            } catch (Exception e) {
//                System.err.println("❗ Error sending to " + phone + ": " + e.getMessage());
//            }
//        }
//        // 🎉 Add status to model
//        model.addAttribute("message", "📤 " + sentCount + " WhatsApp reminders sent.");
//        model.addAttribute("members", members);
//        return "members";
//    }
//
//    
////whatsApp message========================================
//    @GetMapping("/broadcast-message")
//    public String sendBroadcastMessage(Model model) {
//        List<Member> members = memberRepository.findAll();
//        int sentCount = 0;
//
//        String announcement = """
//            Hey GymByRaj! 💪
//            Just a heads-up — there’s no water at the gym today, so please don’t forget to bring your own water bottles.
//            Stay hydrated and keep crushing your workouts! 💦
//            """;
//
//        for (Member m : members) {
//            String phone = m.getPhone().trim();
//
//            if (!phone.startsWith("+")) {
//                if (phone.startsWith("0")) {
//                    phone = phone.substring(1);
//                }
//                phone = "+91" + phone;
//            }
//
//            try {
//                whatsAppService.sendMessage(phone, announcement);
//                sentCount++;
//                System.out.println("Broadcast sent to: " + phone);
//            } catch (Exception e) {
//                System.err.println("❌ Failed to send to " + phone + ": " + e.getMessage());
//            }
//        }
//
//        model.addAttribute("message", "📢 Broadcast sent to " + sentCount + " members!");
//        model.addAttribute("members", members);
//        return "members";
//    }
//    @GetMapping("/test-whatsapp")
//    public String testWhatsAppMessage() {
//        whatsAppService.sendMessage("+916264100725", "👋 Hello from GymByRaj! WhatsApp is working.");
//        return "redirect:/members";
//    }
//    @GetMapping("/twilio-test")
//    public String sendTest(Model model) {
//        try {
//            whatsAppService.sendMessage("+916264100725", "🚀 Test from GymByRaj Twilio");
//            model.addAttribute("message", "Test message sent!");
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//        }
//        return "members";
//    }
//
//    //BMI calculator=================================
//        @GetMapping("/bmi")
//        public String showBmiForm(Model model) {
//            model.addAttribute("bmiRequest", new BmiRequest());
//            return "bmi";
//        }
//        @PostMapping("/bmi")
//        public String calculateBmi(@ModelAttribute BmiRequest bmiRequest, Model model) {
//            double heightM = bmiRequest.getHeight() / 100.0;
//            double bmi = bmiRequest.getWeight() / (heightM * heightM);
//            bmi = Math.round(bmi * 10.0) / 10.0;
//
//            String category = getCategory(bmi);
//            double bmiPrime = bmi / 25.0;
//            double ponderalIndex = bmiRequest.getWeight() / Math.pow(heightM, 3);
//
//            double minWeight = 18.5 * heightM * heightM;
//            double maxWeight = 25.0 * heightM * heightM;
//
//            model.addAttribute("bmi", bmi);
//            model.addAttribute("category", category);
//            model.addAttribute("bmiPrime", Math.round(bmiPrime * 100.0) / 100.0);
//            model.addAttribute("ponderalIndex", Math.round(ponderalIndex * 10.0) / 10.0);
//            model.addAttribute("healthyRange", String.format("%.1f kg - %.1f kg", minWeight, maxWeight));
//            model.addAttribute("bmiRequest", bmiRequest);
//
//            return "bmi";
//        }
//        private String getCategory(double bmi) {
//            if (bmi < 18.5) return "Underweight";
//            else if (bmi < 25) return "Normal";
//            else if (bmi < 30) return "Overweight";
//            else return "Obese";
//        }
//        
// //role based controller ==============================         
//            @GetMapping("/login")
//            public String login() {
//                return "login"; // returns login.html
//            }
//            
//            @GetMapping("/403")
//            public String accessDenied() {
//                return "403"; // this will load 403.html
//            }
//            
//            @PreAuthorize("hasAuthority('ROLE_MEMBER')")
//            @GetMapping("/members/dashboard")
//            public String memberDashboard(Model model, Principal principal) {
//                String email = principal.getName();
//                Member member = memberRepository.findByEmail(email)
//                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//                model.addAttribute("member", member);
//                return "member_dashboard";
//            }
//            
//            @GetMapping("/admin/dashboard")
//            @PreAuthorize("hasRole('ADMIN')")
//            public String showAdminDashboard() {
//                return "admin_dashboard"; // This maps to your HTML page
//            }
//            
//}
