package com.hubert.mangocms.domain.requests.asset;

import org.springframework.web.multipart.MultipartFile;

public record UploadAsset(
        String name,
        MultipartFile file
) {}
