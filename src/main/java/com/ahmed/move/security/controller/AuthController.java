package com.ahmed.move.security.controller;

import com.ahmed.move.dto.GeneralResponseDto;
import com.ahmed.move.security.dto.auth.LoginRequestDTO;
import com.ahmed.move.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user.")
    public ResponseEntity<GeneralResponseDto> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(GeneralResponseDto.builder()
                .time(LocalDateTime.now())
                .data(Map.of(
                        "message", "User is successfully logged in",
                        "data", userService.login(dto)))
                .build()
        );
    }

}
