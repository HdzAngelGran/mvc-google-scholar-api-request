package org.arkn37.client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private SearchMetadata search_metadata;
    private String error;
}
