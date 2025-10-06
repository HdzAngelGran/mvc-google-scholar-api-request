package org.arkn37.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.arkn37.model.Article;
import org.arkn37.model.Author;

public class Mapper {

    private Mapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Author toAuthor(JsonObject authorObject) {
        Author author = new Author();

        author.setAuthorId(authorObject.has("author_id") ? authorObject.get("author_id").getAsString() : "");
        author.setName(authorObject.has("name") ? authorObject.get("name").getAsString() : "");
        author.setEmail(authorObject.has("email") ? authorObject.get("email").getAsString() : "");
        author.setAffiliations(authorObject.has("affiliations") ? authorObject.get("affiliations").getAsString() : "");
        author.setThumbnail(authorObject.has("thumbnail") ? authorObject.get("thumbnail").getAsString() : "");

        return author;
    }

    public static Article toArticle(JsonElement jsonElement) {
        JsonObject articleObject = jsonElement.getAsJsonObject();

        Article article = new Article();
        article.setCitationId(articleObject.has("citation_id") ? articleObject.get("citation_id").getAsString() : ("Unknown"));
        article.setTitle(articleObject.has("title") ? articleObject.get("title").getAsString() : ("Unknown"));
        article.setAuthors(articleObject.has("authors") ? articleObject.get("authors").getAsString() : ("Unknown"));
        article.setPublicationDate(articleObject.has("publication_date") ? articleObject.get("publication_date").getAsString() : ("Unknown"));

        String description = articleObject.has("description") ? articleObject.get("description").getAsString() : ("Unknown");
        article.setDescription(description.length() > 730 ? description.substring(0, 730) + "..." : description);

        article.setLink(articleObject.has("link") ? articleObject.get("link").getAsString() : ("Unknown"));
        if (articleObject.has("cited_by") && articleObject.get("cited_by").isJsonObject()) {
            JsonObject citedByObject = articleObject.getAsJsonObject("cited_by");
            article.setCitedBy(citedByObject.has("value") ? citedByObject.get("value").getAsInt() : 1);
        }
        return article;
    }

}
