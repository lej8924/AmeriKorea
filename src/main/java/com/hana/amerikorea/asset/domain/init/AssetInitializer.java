package com.hana.amerikorea.asset.domain.init;

import com.hana.amerikorea.asset.domain.Asset;
import com.hana.amerikorea.asset.domain.StockInfo;
import com.hana.amerikorea.asset.domain.type.Sector;
import com.hana.amerikorea.asset.repository.AssetRepository;
import com.hana.amerikorea.asset.repository.StockInfoRepository;
import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Order(3)
public class AssetInitializer implements CommandLineRunner {

    private final AssetRepository assetRepository;
    private final StockInfoRepository stockInfoRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public AssetInitializer(AssetRepository assetRepository, StockInfoRepository stockInfoRepository, MemberRepository memberRepository) {
        this.assetRepository = assetRepository;
        this.stockInfoRepository = stockInfoRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) {
        try {
            // 멤버를 데이터베이스에서 조회
            Optional<Member> member1 = memberRepository.findByEmail("test@test.com");

            // StockInfo를 stock_name을 기준으로 조회
            StockInfo stockInfo1 = stockInfoRepository.findByStockName("Naver")
                    .orElseGet(() -> new StockInfo("Naver", "035420", Sector.IT, "Internet Services"));

            StockInfo stockInfo2 = stockInfoRepository.findByStockName("삼성SDI")
                    .orElseGet(() -> new StockInfo("삼성SDI", "006400", Sector.IT, "Electronics"));

            // 존재하지 않는 StockInfo는 새로 저장
            if (stockInfo1.getStockName() == null) {
                stockInfoRepository.save(stockInfo1);
            }
            if (stockInfo2.getStockName() == null) {
                stockInfoRepository.save(stockInfo2);
            }

            // Asset 생성 및 Member와 StockInfo와 연관 설정
            Asset asset1 = new Asset(stockInfo1, 2, 74440.0, 3000.0,true);
            asset1.setMember(member1.get()); // member1과 연결

            Asset asset2 = new Asset(stockInfo2, 4, 25000.0, 1200.0,true);
            asset2.setMember(member1.get()); // member1과 연결

            // Asset 저장
            assetRepository.save(asset1);
            assetRepository.save(asset2);
        } catch (Exception e) {
            // 예외 발생 시 스택 트레이스를 출력하여 문제를 확인
            e.printStackTrace();
            // 또는 로깅을 통해 예외 내용을 기록
            Logger.getLogger(AssetInitializer.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
