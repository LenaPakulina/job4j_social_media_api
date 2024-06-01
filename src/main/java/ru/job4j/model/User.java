package ru.job4j.model;

import jakarta.persistence.*;
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
    private int id;

    @Builder.Default
    private LocalDateTime registered = LocalDateTime.now();

    private String name;

    private String email;

    private String password;

    @Column(name = "user_zone")
    private String timezone;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_user_id")
    @Builder.Default
    private List<Subscriber> subscribers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Builder.Default
    private List<Post> allPosts = new ArrayList<>();
}
