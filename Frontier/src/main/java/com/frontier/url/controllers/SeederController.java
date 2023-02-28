package com.frontier.url.controllers;


import com.frontier.url.domains.Websites;
import com.frontier.url.dto.WebsitesDto;
import com.frontier.url.repository.WebsiteRepository;
import com.frontier.url.services.crawler.Crawler;
import com.frontier.url.services.seeder.SeederServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seeder")
public class SeederController {

    private final SeederServiceImp seederService;
    private final Crawler crawler;
    private final WebsiteRepository repository;

    @GetMapping("/seederQueue")
    public void getWebsites() throws Exception {
        List<String> queue = seederService.getUrlsForQueue();


        crawler.getDocuments("A Light");
//        crawler.establisheConnection();

    }

    @GetMapping("/getData")
    public Iterable<Websites> getData() throws Exception {


        return repository.findAll();
//        crawler.establisheConnection();

    }
}
