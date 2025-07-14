package com.satyaprakash.dataset.jsonquery.service;

import com.satyaprakash.dataset.jsonquery.dto.ApiResponse;
import com.satyaprakash.dataset.jsonquery.dto.QueryResponse;

import java.util.Map;

public interface DatasetService {
    ApiResponse insertRecord(String datasetName, Map<String, Object> record);
    QueryResponse queryDataset(String datasetName, String groupBy, String sortBy, String order);
}
