package ru.job4j.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Builder.Default
    @Schema(description = "Date of creation of the post", example = "2022-06-11T14:09")
    private LocalDateTime created = LocalDateTime.now();

    @NotBlank(message = "параметр название (title) не может быть пустым")
    @Schema(description = "A brief description of the post")
    private String title;

    @NotBlank(message = "параметр описание (description) не может быть пустым")
    @Schema(description = "Full description of the post")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "The user associated with the post ")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    @Builder.Default
    private List<File> files = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    @Builder.Default
    private List<PostUpdateHistory> updateHistory = new ArrayList<>();
}
