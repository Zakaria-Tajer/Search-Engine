package com.frontier.url.mapper;

import com.frontier.url.domains.Ranking;
import com.frontier.url.dto.SearchResponseDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-20T17:13:01+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
public class RankingMapperImpl implements RankingMapper {

    @Override
    public Ranking searchResponseDtoToRanking(SearchResponseDto searchResponseDto) {
        if ( searchResponseDto == null ) {
            return null;
        }

        Ranking ranking = new Ranking();

        return ranking;
    }
}
