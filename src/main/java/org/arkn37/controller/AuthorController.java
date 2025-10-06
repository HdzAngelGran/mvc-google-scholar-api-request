package org.arkn37.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import org.arkn37.client.SerpApiClient;
import org.arkn37.model.Article;
import org.arkn37.model.Author;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AuthorController {

    @FXML
    private Label name;

    @FXML
    private Label email;

    @FXML
    private Label affiliations;

    @FXML
    private ImageView thumbnail;

    @FXML
    private ProgressIndicator loadingAuthor;

    @FXML
    private HBox authorInfoBox;

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

    public List<Article> getArticles() {
        return this.author.getArticles();
    }

    public void setArticles(List<Article> articles) {
        this.author.setArticles(articles);
    }

    public void resetAuthor() {
        setName("");
        setEmail("");
        setAffiliations("");
        setThumbnail("");
        setArticles(Collections.emptyList());
        updateView();
    }

    public void updateAuthor(Author author) {
        setName(author.getName());
        setEmail(author.getEmail());
        setAffiliations(author.getAffiliations());
        setThumbnail(author.getThumbnail());
        setArticles(author.getArticles());
        updateView();
    }

    public void updateView() {
        name.setText(getName());
        email.setText(getEmail());
        affiliations.setText(getAffiliations());
        if (getThumbnail() != null && !getThumbnail().isEmpty()) {
            thumbnail.setImage(new Image(getThumbnail(), true));
        }
        authorInfoBox.setVisible(getName() != null && !getName().isEmpty());
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
            authorInfoBox.setVisible(false);
        });

        getAuthorTask.setOnSucceeded(event -> {
            loadingAuthor.setVisible(false);
            Author newAuthor = getAuthorTask.getValue();
            updateAuthor(newAuthor);
            authorInfoBox.setVisible(true);
        });

        getAuthorTask.setOnFailed(event -> {
            resetAuthor();
            loadingAuthor.setVisible(false);
            authorInfoBox.setVisible(false);
            getAuthorTask.getException().printStackTrace();
        });

        new Thread(getAuthorTask).start();
    }

    @FXML
    void showArticles() {
        if (author == null || author.getArticles() == null || author.getArticles().isEmpty()) {
            System.out.println("No articles found for this author.");
            return;
        }

        try {
            FXMLLoader articleListLoad = new FXMLLoader(getClass().getResource("../view/ArticleList.fxml"));

            Parent articleList = articleListLoad.load();

            ArticleListController articleListController = articleListLoad.getController();

            articleListController.setArticles(author.getArticles());

            Stage popupStage = new Stage();
            popupStage.setTitle(author.getName() + "'s Articles");
            popupStage.setScene(new Scene(articleList));

            popupStage.initModality(Modality.APPLICATION_MODAL);

            Stage owner = (Stage) name.getScene().getWindow();
            popupStage.initOwner(owner);

            popupStage.showAndWait();

        } catch (IOException e) {
            System.err.println("Failed to open articles window.");
            e.printStackTrace();
        }
    }

}
