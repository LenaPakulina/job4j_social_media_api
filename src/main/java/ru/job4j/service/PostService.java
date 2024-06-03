package ru.job4j.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.model.File;
import ru.job4j.model.Post;
import ru.job4j.repository.FileRepository;
import ru.job4j.repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FileRepository fileRepository;

    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public boolean updatePost(Post post) {
        int result = postRepository.updateTitleAndDesc(
                post.getTitle(), post.getDescription(), post.getId()
        );
        fileRepository.deleteAllFilesByPostId(post.getId());
        for (File file : post.getFiles()) {
            fileRepository.save(file);
        }
        return result > 0;
    }
}
