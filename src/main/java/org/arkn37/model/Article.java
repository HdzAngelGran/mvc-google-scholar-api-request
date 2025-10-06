package org.arkn37.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Article {
    private int id;

    private String citationId;
    private String title;
    private String authors;
    private String publicationDate;
    private String description;
    private String link;
    private String keywords;
    private int citedBy;
}
