package com.hubert.mangocms.controllers.asset;

import com.hubert.mangocms.domain.annotations.Restricted;
import com.hubert.mangocms.domain.exceptions.asset.AssetUploadException;
import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.asset.Asset;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.asset.UploadAsset;
import com.hubert.mangocms.services.assets.AssetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/application/{applicationId}/media")
public class AssetController {
    private final AssetsService assetsService;

    @Restricted
    @GetMapping("/")
    public List<Asset> findApplicationAssets(@RequestAttribute User user, @PathVariable String applicationId) throws
            InvalidRequestException {
        return assetsService.findApplicationAssets(user, applicationId);
    }

    @Restricted
    @PostMapping("/upload/")
    public Asset upload(
            @PathVariable String applicationId,
            @ModelAttribute UploadAsset uploadAsset,
            @RequestAttribute User user
    ) throws AssetUploadException {
        return assetsService.upload(user, applicationId, uploadAsset);
    }
}
