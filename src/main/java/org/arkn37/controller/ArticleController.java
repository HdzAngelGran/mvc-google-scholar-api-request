package org.arkn37.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import org.arkn37.model.Article;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ArticleController {

    @FXML
    private Label title;

    @FXML
    private Label authors;

    @FXML
    private Label summary;

    @FXML
    private Label publicationDate;

    @FXML
    private Hyperlink link;

    @FXML
    private Label keywords;

    @FXML
    private Label citedBy;

    private Article article;

    public void setArticle(Article article) {
        this.article = article;
        updateView();
    }

    private void updateView() {
        title.setText(article.getTitle());
        authors.setText(article.getAuthors());
        summary.setText(article.getDescription());
        publicationDate.setText(article.getPublicationDate());
        link.setText(article.getLink());
        keywords.setText(article.getKeywords());
    }

    @FXML
    private void openLink() {
        if (article != null && article.getLink() != null && !article.getLink().isEmpty()) {
            try {
                Desktop.getDesktop().browse(new URI(article.getLink()));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
