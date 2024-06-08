package ru.job4j.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users",
       uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Schema(description = "User Model Information")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Schema(description = "Date of user registration", example = "2022-06-11T14:09")
    @Builder.Default
    private LocalDateTime registered = LocalDateTime.now();

    @Schema(example = "Mediator")
    @NotBlank(message = "имя пользователя не может быть пустым")
    private String name;

    @Schema(example = "name@mail.ru")
    @NotBlank(message = "email пользователя не может быть пустым")
    private String email;

    @Schema(example = "12345")
    @NotBlank(message = "password пользователя не может быть пустым")
    private String password;

    @Schema(example = "Asia/Yekaterinburg")
    @Column(name = "user_zone")
    private String timezone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
