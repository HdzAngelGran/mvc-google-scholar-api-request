package org.arkn37.client;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.arkn37.client.model.ResponseAuthor;
import org.arkn37.client.model.ResponseProfile;
import org.arkn37.client.model.SearchMetadata;
import org.arkn37.model.Author;

import java.net.URI;
import java.util.List;

public class SerpApiClient {
    private static final String BASE_URL = "https://serpapi.com/search";
    private static final String API_KEY = "515621ce9fcb5b8864e73cb9f9fbbc0dc297fd933a0617c32ff1a02253e891ac";
    private static final String[] ENGINES = {"google_scholar", "google_scholar_author"};
    private static final String RESTRICTOR = "{search_metadata, profiles.authors, author}";
    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

    private void checkResponseStatus(SearchMetadata searchMetadata) throws Exception {
        if (!searchMetadata.getStatus().equals("Success"))
            throw new Exception("Status: " + searchMetadata.getStatus());
    }

    public Author getAuthorById(String authorId) throws Exception {
        HttpGet request = new HttpGet(BASE_URL);

        URI uri = new URIBuilder(request.getUri())
                .addParameter("engine", ENGINES[1])
                .addParameter("author_id", authorId)
                .addParameter("api_key", API_KEY)
                .addParameter("json_restrictor", RESTRICTOR)
                .build();
        request.setUri(uri);

        String response = HTTP_CLIENT.execute(request,
                res -> {
                    if (res.getCode() >= 200 && res.getCode() < 300) return EntityUtils.toString(res.getEntity());
                    throw new RuntimeException("Error: " + res.getCode() + " - Failed to fetch data: " + res.getReasonPhrase());
                });

        ResponseAuthor responseAuthor = new Gson().fromJson(response, ResponseAuthor.class);
        checkResponseStatus(responseAuthor.getSearch_metadata());

        return responseAuthor.getAuthor();
    }

    public List<Author> getAuthorByName(String name) throws Exception {
        HttpGet request = new HttpGet(BASE_URL);

        URI uri = new URIBuilder(request.getUri())
                .addParameter("engine", ENGINES[0])
                .addParameter("q", "author:" + name)
                .addParameter("api_key", API_KEY)
                .addParameter("json_restrictor", RESTRICTOR)
                .build();
        request.setUri(uri);

        String response = HTTP_CLIENT.execute(request,
                res -> {
                    if (res.getCode() >= 200 && res.getCode() < 300) return EntityUtils.toString(res.getEntity());
                    throw new RuntimeException("Error: " + res.getCode() + " - Failed to fetch data: " + res.getReasonPhrase());
                });

        ResponseProfile responseAuthorsList = new Gson().fromJson(response, ResponseProfile.class);
        checkResponseStatus(responseAuthorsList.getSearch_metadata());

        return responseAuthorsList.getProfiles().getAuthors();
    }

}
