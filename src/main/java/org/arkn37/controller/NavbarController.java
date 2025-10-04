package org.arkn37.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import lombok.Setter;

@Setter
public class NavbarController {

    private MainController mainController;

    @FXML
    void handlePageAction(ActionEvent event) {
        Node source = (Node) event.getSource();
        String pageName = (String) source.getUserData();
        mainController.changePage(pageName);
    }
}
