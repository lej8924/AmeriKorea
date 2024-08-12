package com.hana.amerikorea.portfolio.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class NaverNewsResponse {

    private List<Item> items;

    @Getter
    @Setter
    public static class Item{
        private String title;
        private String link;
        private String description;
        private String pubDate;
    }
}
