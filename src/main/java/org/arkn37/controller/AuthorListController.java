package org.arkn37.controller;

import org.arkn37.client.SerpApiClient;
import org.arkn37.model.Author;
import org.arkn37.view.AuthorListView;

import java.util.List;

public class AuthorListController {
    private List<Author> authorList;
    private final AuthorListView authorListView;
    private final SerpApiClient serpApiClient = new SerpApiClient();

    public AuthorListController(List<Author> authorList, AuthorListView authorListView) {
        this.authorList = authorList;
        this.authorListView = authorListView;
    }

    public void updateAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public void updateView() {
        this.authorListView.printAuthorListDetails(authorList);
    }

    public String getAuthorIdByPosition(int position) {
        return authorList.get(position).getAuthor_id();
    }

    public void getAuthorByName(String name) throws Exception {
        List<Author> authors = serpApiClient.getAuthorByName(name);
        updateAuthorList(authors);
    }

}
