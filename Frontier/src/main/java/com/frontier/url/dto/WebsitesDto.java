package com.frontier.url.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebsitesDto {

    private int websiteId;
    private String websiteUrl;
    private String isFetched;
}
