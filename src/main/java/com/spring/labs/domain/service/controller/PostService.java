package com.spring.labs.domain.service.controller;

import com.spring.labs.domain.entity.post.Post;
import com.spring.labs.domain.entity.post.PostRepository;
import com.spring.labs.domain.entity.tag.Tag;
import com.spring.labs.domain.web.dto.PostDto;
import com.spring.labs.domain.web.exception.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional
    public Post savePost(PostDto dto) {
        return repository.save(Post.builder()
                .title(dto.title())
                .tag(getTagFromString(dto.tag()))
                .content(dto.content())
                .build());
    }

    @Transactional
    public Set<Tag> getTagFromString(String s) {
        Set<Tag> tagSet = new LinkedHashSet<>();
        String[] tags = s.split(",");
        for (String tag : tags) {
            tagSet.add(Tag.builder()
                    .name(tag.trim())
                    .build());
        }
        return tagSet;
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
