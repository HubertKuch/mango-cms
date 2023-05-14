package com.hubert.mangocms.controllers.blog;

import com.hubert.mangocms.domain.aggregators.BlogAggregator;
import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.blog.CreateBlog;
import com.hubert.mangocms.domain.responses.AggregatedBlog;
import com.hubert.mangocms.domain.responses.BaseResponse;
import com.hubert.mangocms.services.blog.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class BlogController {
    private final BlogService blogService;
    private final BlogAggregator blogAggregator;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{applicationId}/blogs/")
    public AggregatedBlog createBlog(
            @RequestAttribute User user, @RequestBody CreateBlog createBlog, @PathVariable String applicationId
    ) throws InvalidRequestException {
        Blog blog = blogService.createBlog(user, applicationId, createBlog);

        return blogAggregator.aggregatedBlog(blog);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{applicationId}/blogs/{blogId}/")
    public BaseResponse delete(
            @RequestAttribute User user, @PathVariable String applicationId, @PathVariable String blogId
    ) throws InvalidRequestException {
        blogService.delete(user, applicationId, blogId);

        return new BaseResponse("OK");
    }
}
