package com.hubert.mangocms.domain.aggregators;

import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.domain.responses.AggregatedBlog;
import com.hubert.mangocms.domain.responses.AggregatedField;
import com.hubert.mangocms.services.application.ApplicationBlogFieldRepresentationService;
import com.hubert.mangocms.services.blog.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BlogAggregator {

    private final BlogService blogService;
    private final FieldAggregator fieldAggregator;
    private final ApplicationBlogFieldRepresentationService representationService;

    public AggregatedBlog aggregatedBlog(String id) throws InvalidRequestException {
        Blog blog = blogService.findById(id).orElseThrow(() -> new InvalidRequestException("Invalid blog id"));

        return aggregatedBlog(blog);
    }

    public AggregatedBlog aggregatedBlog(Blog blog) {
        List<AggregatedField> aggregatedFields = representationService
                .findByBlog(blog)
                .stream()
                .map(fieldAggregator::aggregateField)
                .toList();

        return new AggregatedBlog(blog, aggregatedFields);
    }

    public List<AggregatedBlog> aggregatedBlogs(List<Blog> blogs) {
        return blogs.stream().map(this::aggregatedBlog).collect(Collectors.toList());
    }

}
