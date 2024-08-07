package com.hana.amerikorea.asset.domain.init;


import com.hana.amerikorea.asset.domain.AssetDomain;
import com.hana.amerikorea.asset.dto.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AssetDomainInitializer implements CommandLineRunner {

    private final AssetRepository assetRepository;

    @Autowired
    public AssetDomainInitializer(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 더미 데이터 삽입
        AssetDomain asset1 = new AssetDomain(1, "삼성전자", 2000, 30000);
        AssetDomain asset2 = new AssetDomain(2, "SK하이닉스", 1500, 30000);
        AssetDomain asset3 = new AssetDomain(3, "현대로템", 1800, 30000);
        AssetDomain asset4 = new AssetDomain(4, "LG전자", 2500, 30000);
        AssetDomain asset5 = new AssetDomain(5, "네이버", 3000, 30000);

        assetRepository.save(asset1);
        assetRepository.save(asset2);
        assetRepository.save(asset3);
        assetRepository.save(asset4);
        assetRepository.save(asset5);
    }
}
