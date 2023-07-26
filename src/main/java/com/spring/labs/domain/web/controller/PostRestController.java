package com.spring.labs.domain.web.controller;

import com.spring.labs.domain.entity.post.Post;
import com.spring.labs.domain.service.controller.PostService;
import com.spring.labs.domain.web.dto.PostDto;
import com.spring.labs.util.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Slf4j
public class PostRestController {

    private final PostService service;

    @GetMapping("")
    public ResponseData.ApiResult<List<PostDto>> showAllPosts() {
        return ResponseData.success(service.findAll().stream()
                .map(Post::of)
                .toList(), "조회 완료");
    }

    @GetMapping("/{id}")
    public ResponseData.ApiResult<PostDto> showPostById(@PathVariable Long id) {
        return ResponseData.success(service.findById(id).of(), "조회 완료");
    }

    @PostMapping("")
    public ResponseData.ApiResult<PostDto> createNewPost(@RequestBody PostDto dto) {
        return ResponseData.success(service.savePost(dto).of(), "저장 성공");
    }

    @PutMapping("/{id}")
    public ResponseData.ApiResult<PostDto> updatePostById(@PathVariable Long id, @RequestBody PostDto dto) {
        return ResponseData.success(service.updateById(id, dto).of(), "수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseData.ApiResult<?> deletePostById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseData.success(null, "삭제 완료");
    }

}
