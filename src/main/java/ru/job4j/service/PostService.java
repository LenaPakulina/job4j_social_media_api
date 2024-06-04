package ru.job4j.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.dto.UserPostsDto;
import ru.job4j.dto.UserPostsMapper;
import ru.job4j.model.File;
import ru.job4j.model.Post;
import ru.job4j.model.Post;
import ru.job4j.model.User;
import ru.job4j.repository.FileRepository;
import ru.job4j.repository.PostRepository;
import ru.job4j.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public boolean updatePostAndFiles(Post post) {
        int result = postRepository.updateTitleAndDesc(post);
        fileRepository.deleteAllFilesByPostId(post.getId());
        for (File file : post.getFiles()) {
            fileRepository.save(file);
        }
        return result > 0;
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> findById(Integer id) {
        return postRepository.findById(id);
    }

    public boolean deleteById(int id) {
        return postRepository.deletePostById(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public boolean update(Post post) {
        return postRepository.updateTitleAndDesc(post) > 0;
    }

    public List<UserPostsDto> convert(List<Integer> usersIds) {
        List<UserPostsDto> result = new ArrayList<>();
        for (Integer userId : usersIds) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                List<Post> posts = postRepository.findByUserId(userId);
                result.add(UserPostsMapper.getDto(user.get(), posts));
            }
        }
        return result;
    }
}
