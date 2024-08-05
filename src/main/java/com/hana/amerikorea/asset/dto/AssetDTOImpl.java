package com.hana.amerikorea.asset.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AssetDTOImpl implements AssetDTO {

    @Autowired
    private AssetRepository assetRepo; // JPA로 쿼리문 수행


}
