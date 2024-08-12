package com.hana.amerikorea.asset.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetDomainRepository extends JpaRepository<AssetDomain, Long> {

}
