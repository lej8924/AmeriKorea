package com.hana.amerikorea.mypage.dto;

import com.hana.amerikorea.mypage.domain.AssetDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<AssetDomain, Integer> {
}
