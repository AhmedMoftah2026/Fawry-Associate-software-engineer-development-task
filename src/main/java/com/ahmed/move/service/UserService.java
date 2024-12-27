package com.ahmed.move.service;

import com.ahmed.move.dto.auth.LoginDTO;
import com.ahmed.move.dto.auth.LoginResponseDTO;
import com.ahmed.move.enummration.Role;
import com.ahmed.move.jwt.JwtTokenProvider;
import com.ahmed.move.model.User;
import com.ahmed.move.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public LoginResponseDTO login(LoginDTO loginDTO) {
        log.info("Starting user login...");

        try {
            User user = userRepository.findByUsername(loginDTO.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

            if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
                throw new IllegalArgumentException("Invalid password");


            String token = tokenProvider.generateToken(user);

            log.info("user login successful.");
            return LoginResponseDTO.builder()
                    .userId(user.getId())
                    .token(token)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred during user login: {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());

        }
    }



    @PostConstruct
    private void addInitialUsers (){
        User user = new User(null,"user",passwordEncoder.encode("1111"), Role.USER);
        User admin = new User(null,"admin",passwordEncoder.encode("2222"), Role.ADMIN);

        if(userRepository.count() == 0){
            userRepository.saveAll(List.of(user,admin));
        }

    }
}


