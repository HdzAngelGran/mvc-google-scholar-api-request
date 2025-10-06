package org.arkn37.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.arkn37.model.Article;

import java.util.function.Consumer;

public class ArticleListCell extends ListCell<Article> {

    private final boolean justView;
    private final Consumer<Article> onClicked;
    private final VBox contentBox = new VBox(5);
    private final Label title = new Label();
    private final Label authors = new Label();
    private final Button saveButton = new Button();

    public ArticleListCell(Consumer<Article> click) {
        this.onClicked = click;
        this.justView = false;
        title.setFont(Font.font("", FontWeight.BOLD, 14));
        authors.setFont(Font.font("", 12));
        saveButton.setText("Save");
        contentBox.getChildren().addAll(title, authors, saveButton);
    }

    public ArticleListCell() {
        this.onClicked = s -> {
        };
        this.justView = true;
        title.setFont(Font.font("", FontWeight.BOLD, 14));
        authors.setFont(Font.font("", 12));
        saveButton.setText("Save");
        contentBox.getChildren().addAll(title, authors, saveButton);
    }

    @Override
    protected void updateItem(Article article, boolean empty) {
        super.updateItem(article, empty);

        if (empty || article == null) {
            setText(null);
            setGraphic(null);
        } else {
            title.setText(article.getTitle());
            authors.setText(article.getAuthors());
            saveButton.setVisible(!justView);
            if (justView)
                saveButton.setOnAction(event ->
                        onClicked.accept(article)
                );
            setGraphic(contentBox);
        }
    }
}
