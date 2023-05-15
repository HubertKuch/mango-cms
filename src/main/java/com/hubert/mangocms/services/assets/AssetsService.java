package com.hubert.mangocms.services.assets;

import com.hubert.mangocms.domain.exceptions.asset.AssetUploadException;
import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.asset.Asset;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.asset.UploadAsset;
import com.hubert.mangocms.repositories.assets.AssetsRepository;
import com.hubert.mangocms.services.application.ApplicationService;
import com.hubert.mangocms.services.storage.AssetsStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetsService {
    private final AssetsRepository assetsRepository;
    private final ApplicationService applicationService;
    private final AssetsStorageService assetsStorageService;

    @Transactional(rollbackOn = Throwable.class)
    public Asset upload(User loggedInUser, String applicationId, UploadAsset uploadAsset) throws AssetUploadException {
        MultipartFile multipartFile = uploadAsset.file();

        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new AssetUploadException("File cannot be nullable");
        }

        if (multipartFile.getContentType() == null) {
            throw new AssetUploadException("Cannot upload file with nullable mime type");
        }

        try {
            Application application = applicationService.findApplicationOfUser(loggedInUser, applicationId);
            File file = assetsStorageService.upload(uploadAsset);
            DataSize size = DataSize.ofBytes(multipartFile.getSize());
            MediaType mediaType = MediaType.valueOf(multipartFile.getContentType());
            Asset asset = new Asset(file.getName(), mediaType, size, application);

            assetsRepository.save(asset);

            return asset;
        } catch (Throwable throwable) {
            log.error("", throwable);
            throw new AssetUploadException("Cannot upload");
        }
    }

    public List<Asset> findApplicationAssets(User loggedInUser, String applicationId) throws InvalidRequestException {
        Application application = applicationService.findApplicationOfUser(loggedInUser, applicationId);

        return assetsRepository.findAssetsByApplicationAndApplication_User(application, loggedInUser);
    }
}
