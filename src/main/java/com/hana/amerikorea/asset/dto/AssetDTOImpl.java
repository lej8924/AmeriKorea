package com.hana.amerikorea.asset.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AssetDTOImpl implements AssetDTO {

    @Autowired
    private AssetRepository assetRepo;

    @Override
    public List<AssetDTO> getAllAssets() {
        return List.of();
    }
}
