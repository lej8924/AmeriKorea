package com.hana.amerikorea.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockData {
    private String symbol;
    private String name;
    private String sector;
    private String industry;

    public StockData(String symbol,String name, String sector, String industry) {
        this.symbol = symbol;
        this.name = name;
        this.sector = sector;
        this.industry = industry;
    }
}
