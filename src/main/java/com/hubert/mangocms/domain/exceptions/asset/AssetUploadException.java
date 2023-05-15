package com.hubert.mangocms.domain.exceptions.asset;

import com.hubert.mangocms.domain.exceptions.internal.ConflictException;

public class AssetUploadException extends ConflictException {
    public AssetUploadException(String message) {
        super(message);
    }
}
