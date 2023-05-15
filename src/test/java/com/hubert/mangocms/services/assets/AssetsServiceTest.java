package com.hubert.mangocms.services.assets;

import com.hubert.mangocms.domain.exceptions.asset.AssetUploadException;
import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.asset.Asset;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.asset.UploadAsset;
import com.hubert.mangocms.repositories.application.ApplicationRepository;
import com.hubert.mangocms.repositories.assets.AssetsRepository;
import com.hubert.mangocms.services.application.ApplicationService;
import com.hubert.mangocms.services.storage.AssetsStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssetsServiceTest {

    ApplicationRepository applicationRepository;
    ApplicationService applicationService;
    AssetsStorageService assetsStorageService;
    AssetsRepository assetsRepository;
    AssetsService assetsService;

    @BeforeEach
    void findApplicationAssets() {
        applicationRepository = mock(ApplicationRepository.class);
        applicationService = new ApplicationService(applicationRepository);

        assetsStorageService = mock(AssetsStorageService.class);

        assetsRepository = mock(AssetsRepository.class);
        assetsService = new AssetsService(assetsRepository, applicationService, assetsStorageService);
    }

    @Test
    void givenLoggedInUser_thenFetch_shouldReturnValidListOfAssets() throws InvalidRequestException {
        User user = new User();
        Application application = new Application("", user);

        List<Asset> assets = List.of(new Asset());
        String applicationId = application.getId();

        when(applicationRepository.findByIdAndUser(eq(applicationId), eq(user))).thenReturn(Optional.of(application));
        when(assetsRepository.findAssetsByApplicationAndApplication_User(eq(application), eq(user))).thenReturn(assets);

        assertEquals(assets, assetsService.findApplicationAssets(user, applicationId));
    }

    @Test
    void givenValidUploadData_thenSaveAndFetch_shouldReturnValidAsset() throws IOException, AssetUploadException {
        User user = new User();
        Application application = new Application("", user);
        final String FILE_NAME = "test.png";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test",
                FILE_NAME,
                MediaType.IMAGE_PNG_VALUE,
                "test".getBytes()
        );
        File file = new File(FILE_NAME);
        UploadAsset uploadAsset = new UploadAsset(mockMultipartFile);

        String applicationId = application.getId();

        when(applicationRepository.findByIdAndUser(eq(applicationId), eq(user))).thenReturn(Optional.of(application));
        when(assetsStorageService.upload(any())).thenReturn(file);

        Asset given = assetsService.upload(user, applicationId, uploadAsset);

        assertAll(
                () -> assertNotNull(given),
                () -> assertEquals(mockMultipartFile.getOriginalFilename(), given.getName()),
                () -> assertEquals(MediaType.IMAGE_PNG, given.getMediaType())
        );
    }

    @Test
    void givenEmptyFile_thenValidate_shouldThrowException() {
        User user = new User();
        Application application = new Application("", user);
        final String FILE_NAME = "test.png";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test",
                FILE_NAME,
                MediaType.IMAGE_PNG_VALUE,
                new byte[] {}
        );
        UploadAsset uploadAsset = new UploadAsset(mockMultipartFile);

        String applicationId = application.getId();

        when(applicationRepository.findByIdAndUser(eq(applicationId), eq(user))).thenReturn(Optional.of(application));

        assertThrows(AssetUploadException.class, () -> assetsService.upload(user, applicationId, uploadAsset));
    }
}