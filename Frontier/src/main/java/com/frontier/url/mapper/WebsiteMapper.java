package com.frontier.url.mapper;


import com.frontier.url.domains.Websites;
import com.frontier.url.dto.SiteDto;
import com.frontier.url.dto.WebsitesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
public interface WebsiteMapper {

    WebsiteMapper INSTANCE = Mappers.getMapper( WebsiteMapper.class );
//    @Mapping(source = "linksCrawledList", target = "crawledList")
    Websites siteDtoToDomain(SiteDto site);


}
