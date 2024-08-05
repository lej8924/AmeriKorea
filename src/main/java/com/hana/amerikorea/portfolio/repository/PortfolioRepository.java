package com.hana.amerikorea.portfolio.repository;

import com.hana.amerikorea.portfolio.domain.Stock;
import com.hana.amerikorea.portfolio.domain.type.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Stock, String> {
    // 추가적인 메서드를 정의할 수 있습니다. 예를 들어:

    // 특정 섹터에 속한 모든 주식 찾기
    List<Stock> findBySector(Sector sector);

    // 특정 산업에 속한 모든 주식 찾기
    List<Stock> findByIndustry(String industry);

    // 특정 거래소에 상장된 모든 주식 찾기
    List<Stock> findByExchange(String exchange);

    // 특정 국가에 속한 모든 주식 찾기
    List<Stock> findByCountry(String country);
}
