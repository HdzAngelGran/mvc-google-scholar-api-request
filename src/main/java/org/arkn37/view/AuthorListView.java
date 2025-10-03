package org.arkn37.view;

import org.arkn37.model.Author;

import java.util.List;

public class AuthorListView {
    public void printAuthorListDetails(List<Author> authors) {
        authors.forEach(System.out::println);
    }
}
