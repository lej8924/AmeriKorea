package com.hana.amerikorea.asset.service;

import com.hana.amerikorea.asset.domain.AssetDomain;
import com.hana.amerikorea.asset.dto.AssetDTO;

import java.util.List;

public interface AssetService {

    List<AssetDTO> getAllAssets();

    void saveAsset(AssetDomain asset);
}