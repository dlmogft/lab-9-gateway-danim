package gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;

import java.net.URI;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * This is the configuration used when the application is started with test profile:
 * mvn spring-boot:run -Dspring.profiles.active=test
 * then the localhost:8080 redirects with the OAuth autorization in GitHub page
 */
@Configuration
@Profile("test")
public class SecurityConfigTest {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        RedirectServerLogoutSuccessHandler logoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
        logoutSuccessHandler.setLogoutSuccessUrl(URI.create("/"));

        return http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/login**", "/logout**", "/error**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2Login(withDefaults())  // Login OAuth2
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logoutSuccessHandler)
                )
                .build();
    }
}
