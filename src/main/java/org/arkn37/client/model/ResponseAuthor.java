package org.arkn37.client.model;

import lombok.Getter;
import lombok.Setter;
import org.arkn37.model.Author;

@Getter
@Setter
public class ResponseAuthor extends Response {
    private Author author;
}
