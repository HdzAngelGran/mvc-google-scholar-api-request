package org.arkn37.client.model;

import lombok.Getter;
import lombok.Setter;
import org.arkn37.model.Author;

import java.util.List;

@Getter
@Setter
public class Profile {
    private List<Author> authors;
}
