package com.hana.amerikorea.portfolio.dto.request;

public record NaverNewsRequest(
        String query,
        Integer display,
        String sort
) {
}
