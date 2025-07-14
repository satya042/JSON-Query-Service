package com.satyaprakash.dataset.jsonquery.dto;

import lombok.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryResponse {
    private Map<String, List<Map<String, Object>>> groupedRecords;
    private List<Map<String, Object>> sortedRecords;
}
