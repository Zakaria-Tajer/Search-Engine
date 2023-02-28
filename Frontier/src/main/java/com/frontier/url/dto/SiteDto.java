package com.frontier.url.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteDto {

    private String websiteUrl;
    private boolean isFetched;
    private String keywordSearchedFor;
}
