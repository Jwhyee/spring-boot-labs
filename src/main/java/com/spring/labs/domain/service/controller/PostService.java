package com.spring.labs.domain.service.controller;

import com.spring.labs.domain.entity.post.Post;
import com.spring.labs.domain.entity.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(Long id) {
        return repository.findById(id)
                .orElse(null);
    }
}
