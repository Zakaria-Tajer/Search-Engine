package com.frontier.url.services.crawler;


import com.frontier.url.repository.WebsiteRepository;
import com.frontier.url.services.seeder.SeederServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class Crawler {

    private final SeederServiceImp seederService;

    private final WebsiteRepository websiteRepository;

    public HashMap<Integer, String> urlsQueues() throws IOException {

        HashMap<Integer, String> hashMapOfUrls = new HashMap<>();

        for (int i = 0; i < seederService.getUrlsForQueue().size(); i++) {
            hashMapOfUrls.put(i, seederService.getUrlsForQueue().get(i));
        }

        return hashMapOfUrls;
    }

    public List<Document> establisheConnection() throws IOException {
        List<Document> docsList = new ArrayList<>();
        Connection connection = null;
        for (int attempts = 0; attempts < urlsQueues().size(); attempts++) {
            try {
                connection = Jsoup.connect(urlsQueues().get(attempts));

                log.info("connecting to: " + urlsQueues().get(attempts));

                connection.get();

                if (connection.response().statusCode() == 200) {

                    docsList.add(connection.get());
                } else {
                    log.info("connection lost with: " + connection.get().location());
                    return null;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


        return docsList;
    }

    public void getDocuments(String keyword) throws IOException {

        List<CompletableFuture<List<Document>>> fetchedDocs = new ArrayList<>();
        List<CompletableFuture<List<Document>>> documentFutures = new ArrayList<>();

        List<Document> getterDoc = new ArrayList<>();

        for (int i = 0; i < establisheConnection().size(); i++) {
            CompletableFuture<List<Document>> future = CompletableFuture.supplyAsync(() -> {

                try {
                    return establisheConnection();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
            documentFutures.add(future);
        }

        CompletableFuture.allOf(documentFutures.toArray(new CompletableFuture[0])).join();
        log.info("Done fetching all documents");

        List<Document> documents = new ArrayList<>();
        for (CompletableFuture<List<Document>> documentFuture : documentFutures) {
            List<Document> documentList = documentFuture.join();
            documents.addAll(documentList);

        }

        searchForKeyword(documents, keyword);

    }

    public CompletableFuture<Boolean> searchForKeyword(List<Document> future, String keyword) {

        return CompletableFuture.supplyAsync(() -> {
            boolean found = false;
            for (Document document : future) {
                Elements links = document.select("a[href]");
                for (Element link : links) {
                    String linkHref = link.attr("href");
                    String linkText = link.text();
                    if (linkHref.contains(keyword) || linkText.contains(keyword)) {
                        System.out.println("Keyword found in link: " + linkHref);
                        found = true;
                    } else {
                        // Todo: If Not found in link, get the location,
                        //  and search for the keyword with the method establishSearchers();

                        System.out.println(link.attr("href").contains("/**/"));
                    }
                }
                if (document.text().contains(keyword)) {
                    System.out.println("Keyword found in document: " + document.location());
                    found = true;
                } else {
                    System.out.println("Keyword not found in document: " + document.location());
                    found = false;
                }
            }
            if (!found) {
                System.out.println("Keyword not found in any document");
            }
            return found;

        });


    }

    public void establishSearchers(String connectionToURLAndNoResult, Document document, String wordSearchingFor) {
        Queue<String> failedLookups = new LinkedList<>();
        failedLookups.add(connectionToURLAndNoResult);

        Elements elements = document.getElementsContainingText(wordSearchingFor);

        System.out.println(document.title());


    }


}