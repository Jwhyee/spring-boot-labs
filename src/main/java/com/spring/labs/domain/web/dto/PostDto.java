package com.spring.labs.domain.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record PostDto(Long id, String title, String content,
                      List<Map<String, String>> relatedData,
                      LocalDateTime createTime, LocalDateTime modifyDate) {

    public PostDto clone(List<Map<String, String>> data) {
        return new PostDto(id, title, content, data, createTime, modifyDate);
    }

}
