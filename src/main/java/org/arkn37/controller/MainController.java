package org.arkn37.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    @FXML
    private BorderPane mainPane;

    private final Map<String, Parent> pages = new HashMap<>();

    public void initialize() throws IOException {
        FXMLLoader navbarLoader = new FXMLLoader(getClass().getResource("../view/Navbar.fxml"));
        Parent navbar = navbarLoader.load();
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("../view/Home.fxml"));
        Parent home = homeLoader.load();

        pages.put("home", home);

        NavbarController navbarController = navbarLoader.getController();

        navbarController.setMainController(this);

        this.mainPane.setTop(navbar);
        changePage("home");
    }

    public void changePage(String pageName) {
        Parent view = getPageByName(pageName);
        this.mainPane.setCenter(view);
    }

    private Parent getPageByName(String name) {
        return pages.get(name);
    }
}
