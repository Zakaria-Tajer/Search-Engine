package com.frontier.url.services.seeder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontier.url.dto.WebsitesDto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class SeederServiceImp implements SeederService {
    @Override
    public List<String> getUrlsForQueue() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("/home/cosmic-sword/IdeaProjects/Search-Engine/Frontier/src/main/java/com/frontier/url/websites/ListWebsitesConverted.json");
        List<WebsitesDto> websiteDtoList = Arrays.asList(objectMapper.readValue(file, WebsitesDto[].class));

        Queue<WebsitesDto> queue = new LinkedList<>();

        List<String> queueList = new ArrayList<>();
        websiteDtoList.forEach(queue::offer);

        queue.stream().map(
                q -> queueList.add(q.getWebsiteUrl())
        ).collect(Collectors.toList());

        return queueList;

    }
}
