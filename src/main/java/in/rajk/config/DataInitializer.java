package in.rajk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import in.rajk.model.AppUser;
import in.rajk.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin@gmail.com")) {
            AppUser admin = new AppUser();
            admin.setUsername("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // ENCRYPTED
            admin.setRole("ROLE_ADMIN");
            admin.setEnabled(true);
            userRepository.save(admin);
            System.out.println("✅ Admin user inserted with encrypted password.");
        }
    }
}
