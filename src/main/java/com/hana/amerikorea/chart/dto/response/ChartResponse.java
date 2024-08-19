package com.hana.amerikorea.chart.dto.response;

import lombok.*;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
public class ChartResponse {
    private String stockName;
    private String tickerSymbol;
    private List<ChartData> chartData;

    @Data
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class ChartData {
        private String time;
        private Double openPrice;
        private Double closePrice;
        private Double lowPrice;
        private Double highPrice;
        private Long volume;
    }
}

