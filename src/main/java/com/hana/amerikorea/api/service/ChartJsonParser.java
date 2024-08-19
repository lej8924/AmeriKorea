package com.hana.amerikorea.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana.amerikorea.chart.dto.response.ChartResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChartJsonParser {

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<ChartResponse.ChartData> extractChartData(String json) {
        List<ChartResponse.ChartData> chartDataList = new ArrayList<>();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode output2Array = rootNode.get("output2");

            if(output2Array !=null && output2Array.isArray()) {
                for(JsonNode node:output2Array) {
                    String time = node.get("stck_bsop_date").asText();
                    Double closePrice = node.get("stck_clpr").asDouble();
                    Double openPrice = node.get("stck_oprc").asDouble();
                    Double highPrice = node.get("stck_hgpr").asDouble();
                    Double lowPrice = node.get("stck_lwpr").asDouble();
                    Long volume = node.get("acml_vol").asLong();

                    LocalDate date = LocalDate.parse(time, inputFormatter);
                    String formattedDate = date.format(outputFormatter);

                    ChartResponse.ChartData chartData = new ChartResponse.ChartData(
                            formattedDate,
                            openPrice,
                            closePrice,
                            lowPrice,
                            highPrice,
                            volume
                    );
                    chartDataList.add(chartData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();;
            throw new RuntimeException("차트데이터 json parse 실패");
        }
        return chartDataList;
    }

    public List<ChartResponse.ChartData> extractCartData_oversea(String json) {
        return null;
    }
}
