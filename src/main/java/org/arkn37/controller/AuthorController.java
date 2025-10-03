package org.arkn37.controller;

import org.arkn37.client.SerpApiClient;
import org.arkn37.model.Author;
import org.arkn37.model.Interest;
import org.arkn37.view.AuthorView;

import java.util.List;

public class AuthorController {

    private final Author author;
    private final AuthorView authorView;
    private final SerpApiClient serpApiClient = new SerpApiClient();

    public AuthorController(Author author, AuthorView authorView) {
        this.author = author;
        this.authorView = authorView;
    }

    public String getName() {
        return this.author.getName();
    }

    public void setName(String name) {
        this.author.setName(name);
    }

    public String getEmail() {
        return this.author.getEmail();
    }

    public void setEmail(String email) {
        this.author.setEmail(email);
    }

    public String getAffiliations() {
        return this.author.getAffiliations();
    }

    public void setAffiliations(String affiliations) {
        this.author.setAffiliations(affiliations);
    }

    public String getThumbnail() {
        return this.author.getThumbnail();
    }

    public void setThumbnail(String thumbnail) {
        this.author.setThumbnail(thumbnail);
    }

    public List<Interest> getInterests() {
        return this.author.getInterests();
    }

    public void setInterests(List<Interest> interests) {
        this.author.setInterests(interests);
    }

    public void updateAuthor(Author author) {
        setName(author.getName());
        setEmail(author.getEmail());
        setAffiliations(author.getAffiliations());
        setThumbnail(author.getThumbnail());
        setInterests(author.getInterests());
    }

    public void updateView() {
        authorView.printAuthorDetails(getName(), getEmail(), getAffiliations(), getThumbnail(), getInterests());
    }

    public void getAuthorById(String authorId) throws Exception {
        Author authorResponse = serpApiClient.getAuthorById(authorId);
        updateAuthor(authorResponse);
    }

}
