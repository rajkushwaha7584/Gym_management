package in.rajk;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import in.rajk.model.AppUser;
import in.rajk.model.Member;
import in.rajk.repository.MemberRepository;
import in.rajk.repository.UserRepository;

// ✅ Add import
import io.github.cdimascio.dotenv.Dotenv;

@EnableRetry
@EnableScheduling
@SpringBootApplication
public class GymByRajApplication {

    public static void main(String[] args) {
        // ✅ Load .env file
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        // ✅ Set system properties for Spring to use
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("MAIL_USER", dotenv.get("MAIL_USER"));
        System.setProperty("MAIL_PASS", dotenv.get("MAIL_PASS"));
        System.setProperty("WHATSAPP_TOKEN", dotenv.get("WHATSAPP_TOKEN"));
        System.setProperty("WHATSAPP_PHONE_ID", dotenv.get("WHATSAPP_PHONE_ID"));

        SpringApplication.run(GymByRajApplication.class, args);
        System.out.println("\n🚀 GymByRaj Application Started");
    }

    @Bean
    public CommandLineRunner syncMembersToAppUser(MemberRepository memberRepo,
                                                  UserRepository userRepo,
                                                  PasswordEncoder passwordEncoder) {
        return args -> {
            List<Member> members = memberRepo.findAll();
            int created = 0;
            for (Member member : members) {
                String email = member.getEmail();
                if (!userRepo.existsByUsername(email) && member.getPassword() != null && !member.getPassword().isBlank()) {
                    AppUser user = new AppUser();
                    user.setUsername(email);
                    user.setPassword(passwordEncoder.encode(member.getPassword()));
                    user.setRole("ROLE_MEMBER");
                    user.setEnabled(true);
                    userRepo.save(user);
                    created++;
                    System.out.println("✅ Created login for: " + email);
                }
            }
            System.out.println("🔄 Sync complete. Total users created: " + created);
        };
    }
}
