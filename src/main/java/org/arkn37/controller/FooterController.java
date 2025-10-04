package org.arkn37.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

public class FooterController implements Initializable {
    @FXML
    private Label year;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        year.setText("®" + Year.now().getValue());
    }
}
