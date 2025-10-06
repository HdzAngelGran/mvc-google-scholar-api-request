package org.arkn37.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import org.arkn37.model.Article;
import org.arkn37.repository.ArticleRepository;
import org.arkn37.util.Database;
import org.arkn37.view.ArticleListCell;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SavedArticlesController implements Initializable {

    @FXML
    private ListView<Article> articleListView;

    @FXML
    private ProgressIndicator loadingList;

    private final ArticleRepository articleRepository;

    public SavedArticlesController() {
        try {
            this.articleRepository = new ArticleRepository(Database.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        articleListView.setCellFactory(lv -> new ArticleListCell());
        articleListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Article selectedArticle = articleListView.getSelectionModel().getSelectedItem();
                if (selectedArticle != null) {
                    showArticlePopup(selectedArticle);
                }
            }
        });
        loadArticlesFromDatabase();
    }

    public void refresh() {
        loadArticlesFromDatabase();
    }

    private void loadArticlesFromDatabase() {
        Task<List<Article>> loadTask = new Task<>() {
            @Override
            protected List<Article> call() throws Exception {
                return articleRepository.getAll();
            }
        };

        loadTask.setOnRunning(event -> {
            articleListView.getItems().clear();
            loadingList.setVisible(true);
        });

        loadTask.setOnSucceeded(event -> {
            articleListView.getItems().setAll(loadTask.getValue());
            loadingList.setVisible(false);
        });

        loadTask.setOnFailed(event -> {
            loadingList.setVisible(false);
            loadTask.getException().printStackTrace();
        });

        new Thread(loadTask).start();
    }

    private void showArticlePopup(Article article) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/arkn37/view/Article.fxml"));
            Parent root = loader.load();
            ArticleController controller = loader.getController();
            controller.setArticle(article);
            Stage stage = new Stage();
            stage.setTitle("Article Information");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
