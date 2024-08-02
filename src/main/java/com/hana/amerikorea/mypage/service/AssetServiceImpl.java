package com.hana.amerikorea.mypage.service;

import com.hana.amerikorea.mypage.dto.AssetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetDTO assetDTO;


}
