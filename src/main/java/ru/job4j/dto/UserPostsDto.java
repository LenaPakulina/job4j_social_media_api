package ru.job4j.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.job4j.model.Post;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(description = "UserPostsDto Model Information")
public class UserPostsDto {
    @EqualsAndHashCode.Include
    @Positive(message = "userId должен быть положительным числом")
    @Schema(description = "User id", example = "1")
    private int userId;

    @NotBlank(message = "userName не может быть пустым")
    @Length(min = 2,
            max = 20,
            message = "username должно быть не менее 2 и не более 20 символов")
    @Schema(example = "Mediator")
    private String userName;

    private List<Post> posts = new ArrayList<>();
}
