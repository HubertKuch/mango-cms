package com.hubert.mangocms.services.assets;

import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.asset.Asset;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.repositories.assets.AssetsRepository;
import com.hubert.mangocms.services.application.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public final class AssetsService {
    private final AssetsRepository assetsRepository;
    private final ApplicationService applicationService;

    public List<Asset> findApplicationAssets(User loggedInUser, String applicationId) throws InvalidRequestException {
        Application application = applicationService.findApplicationOfUser(loggedInUser, applicationId);

        return assetsRepository.findAssetsByApplicationAndApplication_User(application, loggedInUser);
    }
}
