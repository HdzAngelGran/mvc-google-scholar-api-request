package org.arkn37.view;

import org.arkn37.model.Interest;

import java.util.List;

public class AuthorView {
    public void printAuthorDetails(String name, String email, String affiliations, String thumbnail, List<Interest> interests) {
        System.out.println("Author Details:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Affiliations: " + affiliations);
        System.out.println("Thumbnail: " + thumbnail);
        System.out.println("Interests: " + interests.toString());
    }

}
