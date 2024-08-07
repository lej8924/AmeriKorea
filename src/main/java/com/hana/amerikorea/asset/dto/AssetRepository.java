package com.hana.amerikorea.asset.dto;

import com.hana.amerikorea.asset.domain.AssetDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<AssetDomain, Integer> {

}
