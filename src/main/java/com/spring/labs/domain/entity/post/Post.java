package com.spring.labs.domain.entity.post;

import com.spring.labs.domain.entity.tag.Tag;
import com.spring.labs.domain.web.dto.PostDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @Setter
    private Set<Tag> tag = new LinkedHashSet<>();

    public void updateBasicInfo(PostDto dto) {
        title = dto.title();
        content = dto.content();
    }

    public PostDto of() {
        List<String> tagList = new LinkedList<>();
        tag.forEach(t -> {
            tagList.add(t.getName());
        });
        return new PostDto(id, title, content, tagList.toString(),null);
    }
}
