package org.arkn37.client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMetadata {
    private String id;
    private String status;
    private String created_at;
    private String processed_at;
    private float total_time_taken;
}
