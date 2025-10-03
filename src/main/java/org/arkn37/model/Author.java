package org.arkn37.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Author {
    private String author_id;
    private String name;
    private String email;
    private String affiliations;
    private String thumbnail;
    private List<Interest> interests;
}
