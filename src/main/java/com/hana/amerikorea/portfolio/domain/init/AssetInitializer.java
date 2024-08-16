package com.hana.amerikorea.portfolio.domain.init;

import com.hana.amerikorea.portfolio.domain.Asset;
import com.hana.amerikorea.portfolio.domain.type.Sector;
import com.hana.amerikorea.portfolio.repository.AssetRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class AssetInitializer {

    private final AssetRepository assetRepository;

    public AssetInitializer(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @PostConstruct
    public void init() {
        Asset asset1 = new Asset("NAVER",5 ,"2024-01-15");
        Asset asset2 = new Asset("삼성",3, "2024-03-20");
//        Asset asset3 = new Asset("TSLA", "Tesla Inc", Sector.IT, "Automotive", "USA", 20, "2023-03-10", 700.0, 2.5);
//        Asset asset4 = new Asset("AMZN", "Amazon.com Inc", Sector.CD, "E-Commerce", "USA", 8, "2023-04-05", 3200.0, 6.0);
//        Asset asset5 = new Asset("MSFT", "Microsoft Corp", Sector.IT, "Technology", "USA", 15, "2023-05-12", 250.0, 4.0);

        assetRepository.save(asset1);
        assetRepository.save(asset2);
//        assetRepository.save(asset3);
//        assetRepository.save(asset4);
//        assetRepository.save(asset5);
    }
}
