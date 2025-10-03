package org.arkn37;

import org.arkn37.controller.AuthorController;
import org.arkn37.controller.AuthorListController;
import org.arkn37.model.Author;
import org.arkn37.view.AuthorListView;
import org.arkn37.view.AuthorView;

import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Author author = new Author();
        AuthorView authorView = new AuthorView();
        AuthorController authorController = new AuthorController(author, authorView);

        List<Author> authors = Collections.emptyList();
        AuthorListView authorListView = new AuthorListView();
        AuthorListController authorListController = new AuthorListController(authors, authorListView);

        try {
            authorListController.getAuthorByName("Angel");
            authorListController.updateView();
            String authorId = authorListController.getAuthorIdByPosition(0);
            authorController.getAuthorById(authorId);
            authorController.updateView();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}