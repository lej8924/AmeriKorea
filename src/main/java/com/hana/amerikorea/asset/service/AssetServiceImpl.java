package com.hana.amerikorea.asset.service;

import com.hana.amerikorea.asset.dto.AssetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetDTO assetDTO;


}
