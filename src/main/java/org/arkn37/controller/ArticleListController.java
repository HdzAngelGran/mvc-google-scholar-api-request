package org.arkn37.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import org.arkn37.client.SerpApiClient;
import org.arkn37.model.Article;
import org.arkn37.repository.ArticleRepository;
import org.arkn37.util.Database;
import org.arkn37.view.ArticleListCell;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ArticleListController implements Initializable {

    @FXML
    private ListView<Article> articleListView;

    @FXML
    private ProgressIndicator loadingList;

    private final SerpApiClient serpApiClient = new SerpApiClient();
    private final ArticleRepository articleRepository;

    public ArticleListController() {
        try {
            this.articleRepository = new ArticleRepository(Database.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        articleListView.setCellFactory(lv -> new ArticleListCell(this::saveArticle));
    }

    private void saveArticle(Article article) {
        loadingList.setVisible(true);
        try {
            Article articleExtraInfo = serpApiClient.getCitationById(article.getCitationId());
            Article articleToSave = joinArticles(article, articleExtraInfo);
            articleRepository.save(articleToSave);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        loadingList.setVisible(false);
    }

    private Article joinArticles(Article article, Article extraInfo) {
        article.setDescription(extraInfo.getDescription());
        article.setPublicationDate(extraInfo.getPublicationDate());
        return article;
    }

    public void setArticles(List<Article> articles) {
        loadingList.setVisible(true);
        articleListView.getItems().setAll(articles);
        loadingList.setVisible(false);
    }
}
