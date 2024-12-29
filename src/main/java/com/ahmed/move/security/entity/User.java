package com.ahmed.move.security.entity;
import com.ahmed.move.security.enummration.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
/*
* We have two users
* admin with password 1111
* user with password 2222
* */
