package com.ahmed.move.security;

import com.ahmed.move.model.User;
import com.ahmed.move.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to load user by username: {}", username);

        Optional<User> myUser = userRepository.findByUsername(username);
        myUser.orElseThrow(() -> {
            log.error("User not found: {}", username);
            return new UsernameNotFoundException("User not found: " + username);
        });

        String password = myUser.get().getPassword();
        String role = ROLE_PREFIX + myUser.get().getRole();
        log.debug("User found: username={}, role={}", username, role);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role));

        log.info("Returning user details for username: {}", username);
        return new CustomUserDetails(username, password, roles);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Initializing NoOpPasswordEncoder bean");
        return NoOpPasswordEncoder.getInstance();
    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
