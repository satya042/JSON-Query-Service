package com.satyaprakash.dataset.jsonquery.service.impl;

import com.satyaprakash.dataset.jsonquery.dto.*;
import com.satyaprakash.dataset.jsonquery.entity.DatasetRecord;
import com.satyaprakash.dataset.jsonquery.repository.DatasetRecordRepository;
import com.satyaprakash.dataset.jsonquery.service.DatasetService;
import com.satyaprakash.dataset.jsonquery.util.JsonUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatasetServiceImpl implements DatasetService {
    private final DatasetRecordRepository repository;

    @Override
    public ApiResponse insertRecord(String datasetName, Map<String, Object> record) {
        String jsonData = JsonUtils.toJson(record);
        DatasetRecord saved = repository.save(DatasetRecord.builder()
                .datasetName(datasetName)
                .jsonData(jsonData)
                .build());
        return ApiResponse.builder()
                .message("Record added successfully")
                .dataset(datasetName)
                .recordId(saved.getId())
                .build();
    }

    @Override
    public QueryResponse queryDataset(String datasetName, String groupBy, String sortBy, String order) {
        List<DatasetRecord> records = repository.findByDatasetName(datasetName);
        List<Map<String, Object>> parsedRecords = records.stream()
                .map(r -> JsonUtils.parseJson(r.getJsonData()))
                .collect(Collectors.toList());

        if (groupBy != null) {
            Map<String, List<Map<String, Object>>> grouped = parsedRecords.stream()
                    .collect(Collectors.groupingBy(r -> String.valueOf(r.get(groupBy))));
            return QueryResponse.builder().groupedRecords(grouped).build();
        } else if (sortBy != null) {
            Comparator<Map<String, Object>> comparator = Comparator.comparing(r -> ((Comparable) r.get(sortBy)));
            if ("desc".equalsIgnoreCase(order)) comparator = comparator.reversed();
            parsedRecords.sort(comparator);
            return QueryResponse.builder().sortedRecords(parsedRecords).build();
        }

        return QueryResponse.builder().sortedRecords(parsedRecords).build();
    }
}
