package com.hana.amerikorea.asset.repository;

import com.hana.amerikorea.asset.domain.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DividendRepository extends JpaRepository<Dividend,Long> {
    List<Dividend> findByAssetStockInfoTickerSymbol(String tickerSymbol);
}
