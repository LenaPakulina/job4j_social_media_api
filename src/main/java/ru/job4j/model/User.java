package ru.job4j.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Positive
    private int id;

    @Builder.Default
    private LocalDateTime registered = LocalDateTime.now();

    @NotBlank(message = "имя пользователя не может быть пустым")
    private String name;

    @NotBlank(message = "email пользователя не может быть пустым")
    private String email;

    @NotBlank(message = "password пользователя не может быть пустым")
    private String password;

    @Column(name = "user_zone")
    private String timezone;
}
