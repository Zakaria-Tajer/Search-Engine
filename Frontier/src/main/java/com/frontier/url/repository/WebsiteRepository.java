package com.frontier.url.repository;

import com.frontier.url.domains.Websites;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebsiteRepository extends ElasticsearchRepository<Websites, String> {

    Optional<Websites> getWebsitesByWebsiteUrlAndAndKeywordSearchedFor(String websiteUrl, String keySearchedFor);
}
