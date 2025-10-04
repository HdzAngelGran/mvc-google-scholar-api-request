package org.arkn37.client.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMetadata {
    private String id;
    private String status;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("processed_at")
    private String processedAt;

    @SerializedName("total_time_taken")
    private float totalTimeTaken;
}
