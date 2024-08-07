package com.hana.amerikorea.asset.domain;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "asset_no", unique = true)
    private long assetNo;

    // 주식명
    @Column(name = "asset_name", nullable = false, length = 50)
    private String assetName;

    // 수량
    @Column(name = "asset_amount")
    private long assetAmount;

    // 구매 가격
    @Column(name = "asset_buy")
    private long assetBuy;

    @Builder
    public AssetDomain(String assetName, long assetAmount, long assetBuy) {
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
