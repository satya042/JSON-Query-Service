package com.satyaprakash.dataset.jsonquery.dto;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApiResponse {
    private String message;
    private String dataset;
    private Long recordId;
}
