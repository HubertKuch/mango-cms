package com.hubert.mangocms.domain.responses;

import com.hubert.mangocms.domain.models.blog.Blog;

import java.util.List;

public record AggregatedBlog(
        Blog blog,
        List<AggregatedField> fields
) {}
