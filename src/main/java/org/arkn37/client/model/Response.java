package org.arkn37.client.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    @SerializedName("search_metadata")
    private SearchMetadata searchMetadata;

    private String error;
}
