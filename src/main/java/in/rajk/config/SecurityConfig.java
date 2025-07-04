package in.rajk.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/", "/register", "/login", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
	            .requestMatchers(
	                "/members",
	                "/members/edit/**",
	                "/members/delete/**",
	                "/members/search",
	                "/export/**",
	                "/send-reminders",
	                "/broadcast-message"
	            ).hasRole("ADMIN")
	            .requestMatchers("/members/dashboard").hasRole("MEMBER")
	            .requestMatchers("/members/details/**").hasAnyRole("ADMIN", "MEMBER")
	            .anyRequest().authenticated()
	        )
	        .formLogin(form -> form
	        	    .loginPage("/login")
	        	    .defaultSuccessUrl("/admin/dashboard", true)
	        	    .failureUrl("/login?error=true")  // 👈 shows error on login page
	        	    .successHandler(customAuthenticationSuccessHandler())
	        	    .permitAll()
	        	)
	        .logout(logout -> logout
	            .logoutSuccessUrl("/login?logout")
	            .permitAll()
	        )
	        .exceptionHandling(exception -> exception
	            .accessDeniedPage("/403") // 👉 this line handles Access Denied cases
	        )
	        .csrf(csrf -> csrf.disable());

	    return http.build();
	}

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    String role = authority.getAuthority();
                    if (role.equals("ROLE_ADMIN")) {
                        response.sendRedirect("/admin/dashboard");
                        return;
                    } else if (role.equals("ROLE_MEMBER")) {
                        response.sendRedirect("/members/dashboard");
                        return;
                    }
                }
                response.sendRedirect("/");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}


