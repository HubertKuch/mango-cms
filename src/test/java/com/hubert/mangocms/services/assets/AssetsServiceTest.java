package com.hubert.mangocms.services.assets;

import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.asset.Asset;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.repositories.application.ApplicationRepository;
import com.hubert.mangocms.repositories.assets.AssetsRepository;
import com.hubert.mangocms.services.application.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssetsServiceTest {

    ApplicationRepository applicationRepository;
    ApplicationService applicationService;
    AssetsRepository assetsRepository;
    AssetsService assetsService;

    @BeforeEach
    void findApplicationAssets() {
        applicationRepository = mock(ApplicationRepository.class);
        applicationService = new ApplicationService(applicationRepository);

        assetsRepository = mock(AssetsRepository.class);
        assetsService = new AssetsService(assetsRepository, applicationService);
    }

    @Test
    void givenLoggedInUser_thenFetch_shouldReturnValidListOfAssets() throws InvalidRequestException {
        User user = new User();
        Application application = new Application("", user);

        List<Asset> assets = List.of(new Asset());
        String applicationId = application.getId();

        when(applicationRepository.findByIdAndUser(eq(applicationId), eq(user)))
                .thenReturn(Optional.of(application));
        when(assetsRepository.findAssetsByApplicationAndApplication_User(eq(application), eq(user))).thenReturn(assets);

        assertEquals(assets, assetsService.findApplicationAssets(user, applicationId));
    }
}