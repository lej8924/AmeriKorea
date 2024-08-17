package com.hana.amerikorea.asset.repository;

import com.hana.amerikorea.asset.domain.StockInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockInfoRepository extends JpaRepository<StockInfo,Long> {

    Optional<StockInfo> findByStockName(String stockName);
    boolean existsByTickerSymbol(String tickerSymbol);
    Optional<StockInfo> findByTickerSymbol(String tickerSymbol);

}
