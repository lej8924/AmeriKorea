package com.hana.amerikorea.asset.service;

import com.hana.amerikorea.asset.dto.AssetDTO;
import com.hana.amerikorea.asset.dto.AssetDTOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetDTO assetDTO;

    @Override
    public List<AssetDTO> getAllAssets() {
        return assetDTO.getAllAssets();
    }
}
