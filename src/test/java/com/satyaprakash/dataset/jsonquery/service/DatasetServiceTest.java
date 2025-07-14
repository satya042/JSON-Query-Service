package com.satyaprakash.dataset.jsonquery.service;

import com.satyaprakash.dataset.jsonquery.dto.ApiResponse;
import com.satyaprakash.dataset.jsonquery.dto.QueryResponse;
import com.satyaprakash.dataset.jsonquery.entity.DatasetRecord;
import com.satyaprakash.dataset.jsonquery.repository.DatasetRecordRepository;
import com.satyaprakash.dataset.jsonquery.service.impl.DatasetServiceImpl;
import com.satyaprakash.dataset.jsonquery.util.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DatasetServiceTest {
    @Mock
    private DatasetRecordRepository repository;

    @InjectMocks
    private DatasetServiceImpl service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertRecord() {
        Map<String, Object> record = Map.of("id", 1, "name", "Jane");
        DatasetRecord entity = DatasetRecord.builder()
                .id(1L)
                .datasetName("test")
                .jsonData(JsonUtils.toJson(record))
                .build();

        when(repository.save(any())).thenReturn(entity);

        ApiResponse response = service.insertRecord("test", record);

        assertEquals("Record added successfully", response.getMessage());
        assertEquals(1L, response.getRecordId());
    }

    @Test
    void testQueryWithGroupBy() {
        DatasetRecord rec = DatasetRecord.builder()
                .id(1L)
                .datasetName("emp")
                .jsonData("{\"id\":1,\"name\":\"Jane\",\"dept\":\"IT\"}")
                .build();
        when(repository.findByDatasetName("emp")).thenReturn(List.of(rec));

        QueryResponse response = service.queryDataset("emp", "dept", null, "asc");

        assertNotNull(response.getGroupedRecords());
        assertTrue(response.getGroupedRecords().containsKey("IT"));
    }

    @Test
    void testQueryWithSortBy() {
        DatasetRecord rec1 = DatasetRecord.builder()
                .id(1L)
                .datasetName("emp")
                .jsonData("{\"id\":1,\"name\":\"A\",\"age\":25}")
                .build();
        DatasetRecord rec2 = DatasetRecord.builder()
                .id(2L)
                .datasetName("emp")
                .jsonData("{\"id\":2,\"name\":\"B\",\"age\":22}")
                .build();

        when(repository.findByDatasetName("emp")).thenReturn(List.of(rec1, rec2));

        QueryResponse response = service.queryDataset("emp", null, "age", "asc");

        assertEquals(2, response.getSortedRecords().size());
        assertEquals("B", response.getSortedRecords().get(0).get("name"));
    }
}
