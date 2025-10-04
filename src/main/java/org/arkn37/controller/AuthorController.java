package org.arkn37.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import lombok.Setter;
import org.arkn37.client.SerpApiClient;
import org.arkn37.model.Author;

import java.util.List;

public class AuthorController {

    @FXML
    private Label name;

    @FXML
    private Label email;

    @FXML
    private Label affiliations;

    @FXML
    private ProgressIndicator loadingAuthor;

    @Setter
    private Author author = new Author();

    private final SerpApiClient serpApiClient = new SerpApiClient();

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

    public List<Object> getInterests() {
        return this.author.getInterests();
    }

    public void setInterests(List<Object> interests) {
        this.author.setInterests(interests);
    }

    public void resetAuthor() {
        setName("");
        setEmail("");
        setAffiliations("");
        setThumbnail("");
        setInterests(null);
        updateView();
    }

    public void updateAuthor(Author author) {
        setName(author.getName());
        setEmail(author.getEmail());
        setAffiliations(author.getAffiliations());
        setThumbnail(author.getThumbnail());
        setInterests(author.getInterests());
        updateView();
    }

    public void updateView() {
        name.setText(getName());
        email.setText(getEmail());
        affiliations.setText(getAffiliations());
    }

    @FXML
    public void getAuthorById(String id) {
        if (id == null || id.trim().isEmpty()) return;

        Task<Author> getAuthorTask = new Task<>() {
            @Override
            protected Author call() throws Exception {
                return serpApiClient.getAuthorById(id);
            }
        };

        getAuthorTask.setOnRunning(event -> {
            resetAuthor();
            loadingAuthor.setVisible(true);
        });

        getAuthorTask.setOnSucceeded(event -> {
            loadingAuthor.setVisible(false);
            Author newAuthor = getAuthorTask.getValue();
            updateAuthor(newAuthor);
        });

        getAuthorTask.setOnFailed(event -> {
            resetAuthor();
            loadingAuthor.setVisible(false);
            getAuthorTask.getException().printStackTrace();
        });

        new Thread(getAuthorTask).start();
    }

}
