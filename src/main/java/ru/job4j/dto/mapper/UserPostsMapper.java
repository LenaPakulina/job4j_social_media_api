package ru.job4j.dto.mapper;

import ru.job4j.dto.UserPostsDto;
import ru.job4j.model.Post;
import ru.job4j.model.User;

import java.util.List;
import java.util.Set;

public class UserPostsMapper {
    public static UserPostsDto getDto(User user, List<Post> posts) {
        UserPostsDto dto = new UserPostsDto();
        dto.setUserId(user.getId());
        dto.setUserName(user.getName());
        dto.setPosts(posts);
        return dto;
    }
}
