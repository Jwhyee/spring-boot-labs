package com.spring.labs.domain.entity.tag;

import com.spring.labs.domain.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
