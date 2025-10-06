package org.arkn37.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.arkn37.client.model.SearchMetadata;
import org.arkn37.model.Article;
import org.arkn37.model.Author;
import org.arkn37.util.Mapper;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SerpApiClient {
    private static final String BASE_URL = "https://serpapi.com/search";
    private static final String KEY = "515621ce9fcb5b8864e73cb9f9fbbc0dc297fd933a0617c32ff1a02253e891ac";
    private static final String[] ENGINES = {"google_scholar", "google_scholar_author"};
    private static final String RESTRICTOR = "{search_metadata, profiles.authors, author, articles, citation}";
    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();
    private static final Gson GSON = new Gson();

    @Getter
    @RequiredArgsConstructor
    private enum PARAMS {
        ENGINE("engine"), API_KEY("api_key"), JSON_RESTRICTOR("json_restrictor");
        private final String value;
    }

    private void checkResponseStatus(SearchMetadata searchMetadata) throws Exception {
        if (!searchMetadata.getStatus().equals("Success"))
            throw new Exception("Status: " + searchMetadata.getStatus());
    }

    public Author getAuthorById(String authorId) throws Exception {
        HttpGet request = new HttpGet(BASE_URL);

        URI uri = new URIBuilder(request.getUri())
                .addParameter(PARAMS.ENGINE.value, ENGINES[1])
                .addParameter(PARAMS.API_KEY.value, KEY)
                .addParameter(PARAMS.JSON_RESTRICTOR.value, RESTRICTOR)
                .addParameter("author_id", authorId)
                .build();
        request.setUri(uri);

        String response = HTTP_CLIENT.execute(request,
                res -> {
                    if (res.getCode() >= 200 && res.getCode() < 300) return EntityUtils.toString(res.getEntity());
                    throw new RuntimeException("Error: " + res.getCode() + " - Failed to fetch data: " + res.getReasonPhrase());
                });

        JsonObject responseAuthor = GSON.fromJson(response, JsonObject.class);

        SearchMetadata searchMetadata = GSON.fromJson(responseAuthor.get("search_metadata").getAsJsonObject(), SearchMetadata.class);
        checkResponseStatus(searchMetadata);

        Author author = Mapper.toAuthor(responseAuthor.get("author").getAsJsonObject());

        List<Article> articles = new ArrayList<>();
        responseAuthor.get("articles").getAsJsonArray().forEach(a -> {
            articles.add(Mapper.toArticle(a));
        });
        author.setArticles(articles);

        return author;
    }

    public List<Author> getAuthorByName(String name) throws Exception {
        HttpGet request = new HttpGet(BASE_URL);

        URI uri = new URIBuilder(request.getUri())
                .addParameter(PARAMS.ENGINE.value, ENGINES[0])
                .addParameter(PARAMS.API_KEY.value, KEY)
                .addParameter(PARAMS.JSON_RESTRICTOR.value, RESTRICTOR)
                .addParameter("q", "author:" + name)
                .build();
        request.setUri(uri);

        String response = HTTP_CLIENT.execute(request,
                res -> {
                    if (res.getCode() >= 200 && res.getCode() < 300) return EntityUtils.toString(res.getEntity());
                    throw new RuntimeException("Error: " + res.getCode() + " - Failed to fetch data: " + res.getReasonPhrase());
                });

        JsonObject responseAuthorsList = GSON.fromJson(response, JsonObject.class);
        SearchMetadata searchMetadata = GSON.fromJson(responseAuthorsList.get("search_metadata").getAsJsonObject(), SearchMetadata.class);
        checkResponseStatus(searchMetadata);

        List<Author> authors = new ArrayList<>();
        responseAuthorsList.get("profiles").getAsJsonObject()
                .get("authors").getAsJsonArray().forEach(a -> {
                    authors.add(Mapper.toAuthor(a.getAsJsonObject()));
                });
        return authors;
    }

    public Article getCitationById(String id) throws Exception {
        HttpGet request = new HttpGet(BASE_URL);

        URI uri = new URIBuilder(request.getUri())
                .addParameter(PARAMS.ENGINE.value, ENGINES[1])
                .addParameter(PARAMS.API_KEY.value, KEY)
                .addParameter(PARAMS.JSON_RESTRICTOR.value, RESTRICTOR)
                .addParameter("view_op", "view_citation")
                .addParameter("citation_id", id)
                .build();
        request.setUri(uri);

        String response = HTTP_CLIENT.execute(request,
                res -> {
                    if (res.getCode() >= 200 && res.getCode() < 300) return EntityUtils.toString(res.getEntity());
                    throw new RuntimeException("Error: " + res.getCode() + " - Failed to fetch data: " + res.getReasonPhrase());
                });

        JsonObject responseCitation = GSON.fromJson(response, JsonObject.class);

        SearchMetadata searchMetadata = GSON.fromJson(responseCitation.get("search_metadata").getAsJsonObject(), SearchMetadata.class);
        checkResponseStatus(searchMetadata);

        return Mapper.toArticle(responseCitation.get("citation"));
    }

}
