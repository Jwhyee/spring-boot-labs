package com.spring.labs.domain.entity.tag;

import com.spring.labs.domain.entity.post.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = "post")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @Setter
    private Post post;
}
