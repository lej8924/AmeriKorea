package com.hana.amerikorea.asset.domain.init;

import com.hana.amerikorea.asset.domain.Asset;
import com.hana.amerikorea.asset.domain.StockInfo;
import com.hana.amerikorea.asset.domain.type.Sector;
import com.hana.amerikorea.asset.repository.AssetRepository;
import com.hana.amerikorea.asset.repository.StockInfoRepository;
import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Order(2)
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
        //try {
            /*// StockInfo 초기화
            StockInfo stockInfo1 = new StockInfo("Naver", "035420", Sector.IT, "Internet Services");
            StockInfo stockInfo2 = new StockInfo("삼성", "005930", Sector.IT, "Electronics");

            stockInfoRepository.save(stockInfo1);
            stockInfoRepository.save(stockInfo2);

            // 멤버를 데이터베이스에서 조회
            Optional<Member> member1 = memberRepository.findByEmail("test@test.com");

            // Asset 생성 및 Member와 StockInfo와 연관 설정
            Asset asset1 = new Asset(stockInfo1, 2, 74440.0, 3000.0);
            asset1.setMember(member1.get()); // member1과 연결

            Asset asset2 = new Asset(stockInfo2, 4, 25000.0, 1200.0);
            asset2.setMember(member1.get()); // member1과 연결

            // Asset 저장
            assetRepository.save(asset1);
            assetRepository.save(asset2);
        } catch (Exception e) {
            // 예외 발생 시 스택 트레이스를 출력하여 문제를 확인
            e.printStackTrace();
            // 또는 로깅을 통해 예외 내용을 기록
            Logger.getLogger(AssetInitializer.class.getName()).log(Level.SEVERE, null, e);*/
        //}
    }
}
