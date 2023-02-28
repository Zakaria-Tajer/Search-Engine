package com.frontier.url.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.frontier.url.services.crawler.LinksCrawled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"websiteId", "isFetched"})
//@JsonIgnoreProperties("websiteId")
public class WebsitesDto {

    private String websiteUrl;

}
