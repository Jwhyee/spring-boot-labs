package com.spring.labs.domain.web.dto;

import java.util.List;
import java.util.Map;

public record PostDto(Long id, String title, String content, String tag,
                      List<Map<String, String>> relatedData) {

    public PostDto clone(List<Map<String, String>> data) {
        return new PostDto(id, title, content, tag, data);
    }

}
