package com.hubert.mangocms.services.blog;

import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.repositories.blog.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public Optional<Blog> findById(String id) {
        return blogRepository.findById(id);
    }
}
