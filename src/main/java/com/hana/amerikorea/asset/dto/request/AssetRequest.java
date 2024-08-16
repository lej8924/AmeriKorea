package com.hana.amerikorea.asset.dto.request;

public record AssetRequest(
        int quantity, // 보유 수량
        String stockName, // 주식 이름
        String purchaseDate, // 주식 구매 날짜
        boolean isKorea // 한국/미국 주식인지?
) {
}
