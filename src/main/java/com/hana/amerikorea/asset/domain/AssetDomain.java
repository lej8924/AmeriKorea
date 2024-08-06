package com.hana.amerikorea.asset.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/* Domain이 VO 역할 */

@Setter
@Getter
@ToString
@Entity
@Table(name = "asset")
@EqualsAndHashCode(of = "asset_no")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssetDomain {

    @Id
    @Column(name = "asset_no", nullable = false, unique = true)
    private int assetNo;

    @Column(name = "asset_name", nullable = false, length = 50)
    private String assetName;

    @Column(name = "asset_amount")
    private int assetAmount;

    @Builder
    public AssetDomain(int assetNo, String assetName, int assetAmount) {
        this.assetNo = assetNo;
        this.assetName = assetName;
        this.assetAmount = assetAmount;
    }

    // 배당 관련 column은 api 구축 후 구현
//    private String dividend_month; // 배당월
//    private int dividend_yield; // 배당률


    /* 아래 동적 데이터는 실시간으로 가져옴
    현재가 (_current) -> kis api로 가져오기
    평가금액 (_eval) = 현재가 x 수량
    수익 (_revenue) = (현재가 - 매입단가) x 수량*/
}
