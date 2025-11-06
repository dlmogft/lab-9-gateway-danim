package gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class SecurityConfig {

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
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    
}
