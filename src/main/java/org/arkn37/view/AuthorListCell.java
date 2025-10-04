package org.arkn37.view;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.arkn37.model.Author;

import java.util.function.Consumer;

public class AuthorListCell extends ListCell<Author> {

    private final Consumer<String> onItemClicked;
    private final VBox contentBox = new VBox(5);
    private final Label nameLabel = new Label();
    private final Label emailLabel = new Label();

    public AuthorListCell(Consumer<String> onItemClicked) {
        this.onItemClicked = onItemClicked;
        nameLabel.setFont(Font.font("", FontWeight.BOLD, 14));
        emailLabel.setFont(Font.font("", 12));

        contentBox.getChildren().addAll(nameLabel, emailLabel);
    }

    @Override
    protected void updateItem(Author author, boolean empty) {
        super.updateItem(author, empty);

        if (empty || author == null) {
            setText(null);
            setGraphic(null);
        } else {
            nameLabel.setText(author.getName());
            emailLabel.setText(author.getEmail());
            contentBox.setOnMouseClicked(event ->
                    onItemClicked.accept(author.getAuthor_id())
            );
            setGraphic(contentBox);
        }
    }
}