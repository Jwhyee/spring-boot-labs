package com.spring.labs.domain.service;

import com.spring.labs.domain.entity.post.Post;
import com.spring.labs.domain.entity.post.PostRepository;
import com.spring.labs.domain.entity.tag.Tag;
import com.spring.labs.domain.web.dto.PostDto;
import com.spring.labs.domain.web.exception.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
        Set<Tag> tagSet = getTagFromString(dto.tag());
        Post newPost = Post.builder()
                .title(dto.title())
                .content(dto.content())
                .tag(tagSet)
                .build();

        for (Tag tag : tagSet) {
            tag.setPost(newPost);
        }

        return repository.save(newPost);
    }

    @Transactional
    public Set<Tag> getTagFromString(String tag) {
        Set<String> strSet = Arrays.stream(tag.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return strSet.stream()
                .map(tagName -> Tag.builder().name(tagName).build())
                .collect(Collectors.toCollection(LinkedHashSet::new));
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
