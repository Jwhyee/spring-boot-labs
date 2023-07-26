package com.spring.labs.domain.web.controller;

import com.spring.labs.domain.service.controller.PostService;
import com.spring.labs.domain.web.dto.PostDto;
import com.spring.labs.util.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Slf4j
public class PostRestController {

    private final PostService service;

    @GetMapping("")
    public ResponseData.ApiResult<?> showAllPosts() {
        return ResponseData.success(service.findAll(), "조회 완료");
    }

    @GetMapping("/{id}")
    public ResponseData.ApiResult<?> showPostById(@PathVariable Long id) {
        return ResponseData.success(service.findById(id), "조회 완료");
    }

    @PostMapping("")
    public ResponseData.ApiResult<?> createNewPost(@RequestBody PostDto dto) {
        return ResponseData.success(service.savePost(dto), "저장 성공");
    }

}
