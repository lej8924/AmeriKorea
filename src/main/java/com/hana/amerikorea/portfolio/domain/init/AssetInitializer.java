package com.hana.amerikorea.portfolio.domain.init;

import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.repository.MemberRepository;
import com.hana.amerikorea.portfolio.domain.Asset;
import com.hana.amerikorea.portfolio.domain.type.Sector;
import com.hana.amerikorea.portfolio.repository.AssetRepository;
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
    private final MemberRepository memberRepository;

    @Autowired
    public AssetInitializer(AssetRepository assetRepository, MemberRepository memberRepository) {
        this.assetRepository = assetRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) {
        try {
            // 멤버를 데이터베이스에서 조회
            Optional<Member> member1 = memberRepository.findByEmail("john.doe@example.com");
            Optional<Member> member2 = memberRepository.findByEmail("jane.smith@example.com");

            // Asset 생성 및 Member와 연관 설정
            Asset asset1 = new Asset(
                    "NAVER", "Naver Corp", Sector.IT, "Internet Services", "South Korea",
                    5, "2024-01-15", 300000.0, 5000.0);
            asset1.setMember(member1.get()); // member1과 연결

            Asset asset2 = new Asset(
                    "삼성", "Samsung Electronics", Sector.IT, "Consumer Electronics", "South Korea",
                    3, "2024-03-20", 80000.0, 3000.0);
            asset2.setMember(member2.get()); // member2와 연결

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
