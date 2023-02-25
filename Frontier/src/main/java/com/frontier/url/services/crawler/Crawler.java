package com.frontier.url.services.crawler;


import com.frontier.url.services.seeder.SeederServiceImp;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Crawler {

    private final SeederServiceImp seederService;

//    public void


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

                System.out.println(" got it:  " + urlsQueues().get(attempts));
                connection.get();


                if (connection.response().statusCode() == 200) {

                    docsList.add(connection.get());
                } else {
                    establishSearchers(connection.get().location(), connection.get(), urlsQueues().get(attempts));
                    System.out.println("nooop");
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

//            System.out.println(attempts);
        }


        return docsList;
    }

    public CompletableFuture<List<List<Document>>> getDocuments(String keyword) throws IOException {
        List<CompletableFuture<List<Document>>> fetchedDocs = new ArrayList<>();

        CompletableFuture<Void> allDocsFetched = CompletableFuture.runAsync(() -> {
            try {
                List<CompletableFuture<List<Document>>> docsFutures = new ArrayList<>();
                for (int i = 0; i < establisheConnection().size(); i++) {
                    CompletableFuture<List<Document>> docFuture = CompletableFuture.supplyAsync(() -> {
                        try {
                            List<Document> docs = establisheConnection(); // fetchDocs() returns a list of documents
                            return docs;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    docsFutures.add(docFuture);
                }
                CompletableFuture.allOf(docsFutures.toArray(new CompletableFuture[0])).join();
                // merge all the lists of documents into a single list
                List<Document> allDocuments = docsFutures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
                fetchedDocs.add(CompletableFuture.completedFuture(allDocuments));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        allDocsFetched.join();
        // return a future that completes with the list of documents
        return CompletableFuture.allOf(fetchedDocs.toArray(new CompletableFuture[0]))
                .thenApply(v -> fetchedDocs.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .thenApply(docs -> {
                    // perform search on the list of documents


                    CompletableFuture<Boolean> isFound = searchForKeyword(docs, keyword);
                    System.out.println("isFound: " + isFound);
                    isFound.thenApply(result -> {
                        if (result) {
                            System.out.println(result + " Success");
                            return "Success";
                        } else {
                            System.out.println(result + " Failure");

                            return "Failure";
                        }
                    });
                    return docs;
                });
    }

    public CompletableFuture<Boolean> searchForKeyword(List<List<Document>> future, String keyword) {
        CompletableFuture<Boolean> result = CompletableFuture.supplyAsync(() -> {
//
            boolean found = false;
            for (List<Document> docs : future) {

                for (Document doc : docs) {

                        Elements links = doc.select("a[href]");
                        for (Element element : links) {
                            String linkHref = element.attr("href");
                            String linkText = element.text();

                            if (linkText.contains(keyword) || linkHref.contains(keyword)) {
                                System.out.println("url: " + doc.location() + linkHref);
                                found = true;
                            }
                        }
                        return true;

                }
            }
            if (!found) {
                System.out.println("Did not find keyword '" + keyword + "' in website");
            }
            return found;

        });

        return result;


    }

    public void establishSearchers(String connectionToURLAndNoResult, Document document, String wordSearchingFor) {
        Queue<String> failedLookups = new LinkedList<>();
        failedLookups.add(connectionToURLAndNoResult);

        Elements elements = document.getElementsContainingText(wordSearchingFor);

        System.out.println(document.title());
//        for (int i = 0; i < elements.size(); i++) {
////            System.out.println(elements.get(i).text());
//
//            if(elements.get(i).text().contains(wordSearchingFor)){
//                System.out.println(elements.get(i).text());
//                break;
//            }else {
//                System.out.println("no");
//                break;
//            }
//        }


    }


}
