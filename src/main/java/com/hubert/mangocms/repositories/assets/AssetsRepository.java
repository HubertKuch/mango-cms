package com.hubert.mangocms.repositories.assets;

import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.asset.Asset;
import com.hubert.mangocms.domain.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetsRepository extends JpaRepository<Asset, String> {
    List<Asset> findAssetsByApplication_User(User user);
    List<Asset> findAssetsByApplicationAndApplication_User(Application application, User applicationUser);
}
