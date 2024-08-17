package com.hana.amerikorea.asset.dto.request;

import lombok.NoArgsConstructor;

public record AssetRequest(
        int quantity, // 보유 수량
        String stockName, // 주식 이름
        Double purchasePrice, // 주식 구매 가격
        boolean isKorea // 한국/미국 주식인지?
) {
}
