package com.spring.labs.domain.web.controller;

import com.spring.labs.domain.service.controller.PostService;
import com.spring.labs.util.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
