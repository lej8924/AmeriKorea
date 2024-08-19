package com.hana.amerikorea.asset.repository;

import com.hana.amerikorea.asset.domain.Asset;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {
    @Query("SELECT a FROM Asset a")
    List<Asset> findAllSorted(Sort sort);

    List<Asset> findByMemberEmail(String email,Sort sort);

    Optional<Asset> findByStockInfoTickerSymbolAndAndMemberEmail(String tickerSymbol,String email);

    boolean existsByStockInfoTickerSymbolAndMemberEmail(String tickerSymbol,String email);

    void deleteByStockInfoTickerSymbolAndMemberEmail(String tickerSymbol,String email);



}
