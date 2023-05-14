package com.hubert.mangocms.repositories.blog;

import com.hubert.mangocms.domain.models.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, String> {}
