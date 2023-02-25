package com.frontier.url.services.seeder;

import com.frontier.url.dto.WebsitesDto;

import java.io.IOException;
import java.util.List;
import java.util.Queue;

public interface SeederService {

    List<String> getUrlsForQueue() throws IOException;

}
