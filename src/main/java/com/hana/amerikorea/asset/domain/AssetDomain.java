package com.hana.amerikorea.asset.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

/* Domain이 VO 역할 */

@Setter
@Getter
@ToString
@Entity
@Table(name = "asset")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssetDomain {

    // data ID
    @Id
    @Column(name = "asset_no", nullable = false, unique = true)
    private int assetNo;

    // 주식명
    @Column(name = "asset_name", nullable = false, length = 50)
    private String assetName;

    // 수량
    @Column(name = "asset_amount")
    private int assetAmount;

    // 구매 가격
    @Column(name = "asset_buy")
    private int assetBuy;

    @Builder
    public AssetDomain(int assetNo, String assetName, int assetAmount, int assetBuy) {
        this.assetNo = assetNo;
        this.assetName = assetName;
        this.assetAmount = assetAmount;
        this.assetBuy = assetBuy;
    }

    // 배당 관련 column은 api 구축 후 구현
//    private String dividend_month; // 배당월
//    private int dividend_yield; // 배당률


    /*
    아래 동적 데이터는 실시간으로 가져옴
    현재가 (_current) -> kis api로 가져오기
    평가금액 (_eval) = 현재가 x 수량
    수익 (_revenue) = (현재가 - 매입단가) x 수량
    */
}
