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
        FXMLLoader footerLoader = new FXMLLoader(getClass().getResource("../view/Footer.fxml"));
        Parent footer = footerLoader.load();
        FXMLLoader searchLoader = new FXMLLoader(getClass().getResource("../view/Search.fxml"));
        Parent search = searchLoader.load();

        pages.put("home", home);
        pages.put("search", search);

        NavbarController navbarController = navbarLoader.getController();

        navbarController.setMainController(this);

        this.mainPane.setTop(navbar);
        changePage("home");
        this.mainPane.setBottom(footer);
    }

    public void changePage(String pageName) {
        Parent view = getPageByName(pageName);
        this.mainPane.setCenter(view);
    }

    private Parent getPageByName(String name) {
        return pages.get(name);
    }
}
