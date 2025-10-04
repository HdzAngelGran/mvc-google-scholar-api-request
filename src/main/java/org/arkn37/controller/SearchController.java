package org.arkn37.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private AnchorPane list;

    @FXML
    private AnchorPane info;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader searchListLoader = new FXMLLoader(getClass().getResource("../view/SearchList.fxml"));
            FXMLLoader authorLoader = new FXMLLoader(getClass().getResource("../view/Author.fxml"));
            Parent searchList = searchListLoader.load();
            Parent author = authorLoader.load();

            SearchListController searchListController = searchListLoader.getController();
            searchListController.setAuthorController(authorLoader.getController());

            list.getChildren().add(searchList);
            info.getChildren().add(author);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
