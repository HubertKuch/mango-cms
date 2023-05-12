package com.hubert.mangocms.domain.requests.blog;

import java.util.List;

public record CreateBlogCredentials(
        List<FieldRepresentationCredentials> fields
) {}
