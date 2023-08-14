package com.spring.labs.domain.service;

import com.spring.labs.domain.entity.post.Post;
import com.spring.labs.domain.entity.post.PostRepository;
import com.spring.labs.domain.web.dto.PostDto;
import com.spring.labs.domain.web.exception.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    @Transactional(readOnly = true)
    public Page<Post> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAllByOrderByCreateDate(pageable);
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id + "번 게시물을 찾을 수 없습니다."));
    }

    @Transactional
    public Post savePost(PostDto dto) {
        Post newPost = Post.builder()
                .title(dto.title())
                .content(dto.content())
                .build();

        return repository.save(newPost);
    }

    @Transactional
    public Post updateById(Long id, PostDto dto) {
        Post currentPost = findById(id);
        currentPost.updateBasicInfo(dto);
        return currentPost;
    }

    public void deleteById(Long id) {
        Post currentPost = findById(id);
        repository.delete(currentPost);
    }
}
