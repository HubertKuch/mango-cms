package com.hubert.mangocms.controllers.blog;

import com.hubert.mangocms.domain.aggregators.BlogAggregator;
import com.hubert.mangocms.domain.annotations.Restricted;
import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.exceptions.internal.NotFoundException;
import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.blog.CreateBlog;
import com.hubert.mangocms.domain.requests.blog.UpdateBlog;
import com.hubert.mangocms.domain.responses.AggregatedBlog;
import com.hubert.mangocms.domain.responses.BaseResponse;
import com.hubert.mangocms.services.blog.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications/{applicationId}/blogs")
public class BlogController {
    private final BlogService blogService;
    private final BlogAggregator blogAggregator;

    @GetMapping("/{blogId}/")
    public AggregatedBlog findById(@PathVariable String applicationId, @PathVariable String blogId) throws
            NotFoundException {
        Blog blog = blogService.findById(blogId).orElseThrow(() -> new NotFoundException("Invalid blog id"));

        return blogAggregator.aggregatedBlog(blog);
    }

    @GetMapping("/blogs/")
    public List<AggregatedBlog> findBlogs(@PathVariable String applicationId) {
        List<Blog> blogs = blogService.findByApplicationId(applicationId);

        return blogAggregator.aggregatedBlogs(blogs);
    }

    @Restricted
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/blogs/")
    public AggregatedBlog createBlog(
            @RequestAttribute User user, @RequestBody CreateBlog createBlog, @PathVariable String applicationId
    ) throws InvalidRequestException {
        Blog blog = blogService.createBlog(user, applicationId, createBlog);

        return blogAggregator.aggregatedBlog(blog);
    }

    @Restricted
    @PutMapping("/blogs/{blogId}/")
    public AggregatedBlog update(
            @PathVariable String applicationId,
            @PathVariable String blogId,
            @RequestAttribute User user,
            @RequestBody UpdateBlog updateBlog
    ) throws InvalidRequestException {
        Blog blog = blogService.update(user, applicationId, blogId, updateBlog);

        return blogAggregator.aggregatedBlog(blog);

    }

    @Restricted
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/blogs/{blogId}/")
    public BaseResponse delete(
            @RequestAttribute User user, @PathVariable String applicationId, @PathVariable String blogId
    ) throws InvalidRequestException {
        blogService.delete(user, applicationId, blogId);

        return new BaseResponse("OK");
    }
}
