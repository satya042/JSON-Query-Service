package com.satyaprakash.dataset.jsonquery.controller;

import com.satyaprakash.dataset.jsonquery.dto.*;
import com.satyaprakash.dataset.jsonquery.service.DatasetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/dataset")
@RequiredArgsConstructor
public class DatasetController {
    private final DatasetService service;

    @PostMapping("/{datasetName}/record")
    public ResponseEntity<ApiResponse> insertRecord(@PathVariable String datasetName,
                                                    @RequestBody Map<String, Object> record) {
        return ResponseEntity.ok(service.insertRecord(datasetName, record));
    }

    @GetMapping("/{datasetName}/query")
    public ResponseEntity<QueryResponse> queryDataset(@PathVariable String datasetName,
                                                      @RequestParam(required = false) String groupBy,
                                                      @RequestParam(required = false) String sortBy,
                                                      @RequestParam(defaultValue = "asc") String order) {
        return ResponseEntity.ok(service.queryDataset(datasetName, groupBy, sortBy, order));
    }
}
