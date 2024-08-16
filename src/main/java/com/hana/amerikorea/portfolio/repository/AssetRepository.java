package com.hana.amerikorea.portfolio.repository;

import com.hana.amerikorea.portfolio.domain.Asset;
import com.hana.amerikorea.portfolio.domain.type.Sector;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {
    @Query("SELECT a FROM Asset a")
    List<Asset> findAllSorted(Sort sort);

}
