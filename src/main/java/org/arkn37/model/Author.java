package org.arkn37.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Author {
    @SerializedName("author_id")
    private String authorId;
    private String name;
    private String email;
    private String affiliations;
    private String thumbnail;
    private List<Object> interests;
}
