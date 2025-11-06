package gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

/**
 * This is the configuration used when the application is started with prod profile:
 * mvn spring-boot:run -Dspring.profiles.active=prod
 * then the login screen is displayed in localhost:8080
 */
@Configuration
@Profile("prod")
public class SecurityConfigProd {

    @Autowired
    SecurityUsersProperties securityUsersProperties;

    @Bean
    public ReactiveUserDetailsService userDetails() {

        List<UserDetails> users = securityUsersProperties.getUsers().stream().map(
                user ->
                        User.withUsername(user.getName())
                                .password(passwordEncoder()
                                        .encode(user.getPassword()))
                                .roles(user.getRoles()).build()
        ).toList();

        return new MapReactiveUserDetailsService(users);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Control of routes by role
                        .pathMatchers("/services/subject/**").permitAll()
                        .pathMatchers("/services/verb/**").permitAll()
                        .pathMatchers("/services/adjective/**").hasRole("ADMIN")
                        .pathMatchers("/services/noun/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers("/services/article/**").hasAnyRole("ADMIN", "USER")
                        // Other routes require authentication
                        .anyExchange().authenticated()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    
}
