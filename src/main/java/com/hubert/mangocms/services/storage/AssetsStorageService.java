package com.hubert.mangocms.services.storage;

import com.hubert.mangocms.configuration.storage.StorageConfiguration;
import com.hubert.mangocms.domain.requests.asset.UploadAsset;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
public class AssetsStorageService {
    private final StorageConfiguration storageConfiguration;

    public File upload(UploadAsset asset) throws IOException {
        String path = getPath() + "/" + buildFileName(
                asset.file().getOriginalFilename(),
                FilenameUtils.getExtension(asset.file().getOriginalFilename())
        );

        File target = new File(path);
        byte[] buffer = asset.file().getBytes();

        Files.write(target.toPath(), buffer);

        return target;
    }

    private String buildFileName(String originalName, String extension) {
        return "%s_%s.%s".formatted(originalName, System.currentTimeMillis(), extension);
    }

    private String getPath() {
        return storageConfiguration.getPath() + storageConfiguration.getAssetsStorageConfiguration().getPath();
    }
}
