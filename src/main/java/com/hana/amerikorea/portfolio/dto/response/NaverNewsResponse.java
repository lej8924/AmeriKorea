package com.hana.amerikorea.portfolio.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Setter
@Getter
public class NaverNewsResponse {

    private List<Item> items;

    @Getter
    @Setter
    public static class Item {
        private String title;
        private String link;
        private String description;
        private String pubDate;

        // 날짜 형식을 변환하는 메서드
        public String getFormattedPubDate() {
            DateTimeFormatter originalFormat = DateTimeFormatter.RFC_1123_DATE_TIME;
            DateTimeFormatter targetDateFormat = DateTimeFormatter.ofPattern("yyyy년 M월 d일 E요일", Locale.KOREAN);
            DateTimeFormatter targetTimeFormat = DateTimeFormatter.ofPattern("HH:mm");

            LocalDateTime dateTime = LocalDateTime.parse(this.pubDate, originalFormat.withZone(ZoneId.of("Asia/Seoul")));
            String formattedDate = dateTime.format(targetDateFormat);
            String formattedTime = dateTime.format(targetTimeFormat);

            return formattedDate + " " + formattedTime;
        }
    }
}