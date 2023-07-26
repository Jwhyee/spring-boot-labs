package com.spring.labs.domain.service.controller;

import com.spring.labs.domain.entity.post.Post;
import com.spring.labs.domain.entity.post.PostRepository;
import com.spring.labs.domain.web.dto.PostDto;
import com.spring.labs.domain.web.exception.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id + "번 게시물을 찾을 수 없습니다."));
    }

    public Post savePost(PostDto dto) {
        return repository.save(Post.builder()
                .title(dto.title())
                .content(dto.content())
                .build());
    }

    @Transactional
    public Post updateById(Long id, PostDto dto) {
        Post currentPost = findById(id);
        currentPost.updateBasicInfo(dto);
        return currentPost;
    }
}
