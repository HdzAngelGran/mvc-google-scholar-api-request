package org.arkn37.controller;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import lombok.Setter;
import org.arkn37.client.SerpApiClient;
import org.arkn37.model.Author;
import org.arkn37.view.AuthorListCell;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchListController implements Initializable {

    @FXML
    private TextField authorName;

    @FXML
    private ListView<Author> authorListView;

    @FXML
    private ProgressIndicator loadingList;

    @Setter
    private AuthorController authorController;

    private final SerpApiClient serpApiClient = new SerpApiClient();

    @FXML
    public void getAuthorByName() {
        String name = authorName.getText();
        if (name == null || name.trim().isEmpty()) return;

        // 1. Create a background task
        Task<List<Author>> searchTask = new Task<>() {
            @Override
            protected List<Author> call() throws Exception {
                return serpApiClient.getAuthorByName(name);
            }
        };

        // 2. What to do before the task starts
        searchTask.setOnRunning(event -> {
            authorListView.getItems().clear();
            loadingList.setVisible(true);
        });

        // 3. What to do when the task succeeds
        searchTask.setOnSucceeded(event -> {
            loadingList.setVisible(false);
            List<Author> authors = searchTask.getValue();
            authorListView.setItems(FXCollections.observableArrayList(authors));
        });

        // 4. What to do if the task fails
        searchTask.setOnFailed(event -> {
            loadingList.setVisible(false);
            searchTask.getException().printStackTrace();
        });

        new Thread(searchTask).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authorListView.setCellFactory(param ->
                new AuthorListCell(this::setAuthorInfo)
        );
    }

    public void setAuthorInfo(String authorId) {
        authorController.getAuthorById(authorId);
    }

    public void reset() {
        authorName.clear();
        authorListView.getItems().clear();
        authorController.resetAuthor();
    }
}
