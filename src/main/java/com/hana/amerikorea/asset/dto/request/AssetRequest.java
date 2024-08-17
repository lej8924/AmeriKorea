package com.hana.amerikorea.asset.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AssetRequest {
        private int quantity; // 보유 수량
        private String stockName; // 주식 이름
        private String purchasePrice; // 주식 구매 가격
        private boolean isKorea; // 한국/미국 주식인지?

        public AssetRequest() {
                this.quantity = 0;
                this.stockName = "";
                this.purchasePrice = "";
                this.isKorea = false;
        }

        @Builder
        public AssetRequest(int quantity, String stockName, String purchasePrice, boolean isKorea) {
                this.quantity = quantity;
                this.stockName = stockName;
                this.purchasePrice = purchasePrice;
                this.isKorea = isKorea;
        }
}

