package com.spring.labs.domain.entity.post;

import com.spring.labs.domain.entity.base.BaseEntity;
import com.spring.labs.domain.web.dto.PostDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    public void updateBasicInfo(PostDto dto) {
        title = dto.title();
        content = dto.content();
    }

    public PostDto of() {
        return new PostDto(id, title, content,null, getCreateDate(), getModifyDate());
    }
}
