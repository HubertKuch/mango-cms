package com.hubert.mangocms.repositories.blog;

import com.hubert.mangocms.domain.models.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, String> {
    List<Blog> findALlByApplication_Id(String applicationId);
}
