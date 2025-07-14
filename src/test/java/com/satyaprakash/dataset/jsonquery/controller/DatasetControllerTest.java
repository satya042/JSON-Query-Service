package com.satyaprakash.dataset.jsonquery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satyaprakash.dataset.jsonquery.dto.ApiResponse;
import com.satyaprakash.dataset.jsonquery.dto.QueryResponse;
import com.satyaprakash.dataset.jsonquery.service.DatasetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DatasetController.class)
public class DatasetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DatasetService service;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testInsertRecord() throws Exception {
        Map<String, Object> record = Map.of("id", 1, "name", "John");
        ApiResponse response = ApiResponse.builder().message("Record added successfully").dataset("employee_dataset").recordId(1L).build();

        when(service.insertRecord(eq("employee_dataset"), any())).thenReturn(response);

        mockMvc.perform(post("/api/dataset/employee_dataset/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Record added successfully"));
    }

    @Test
    void testQueryDatasetWithGroupBy() throws Exception {
        Map<String, List<Map<String, Object>>> grouped = Map.of(
                "Engineering", List.of(Map.of("id", 1, "name", "John", "department", "Engineering"))
        );
        QueryResponse response = QueryResponse.builder().groupedRecords(grouped).build();

        when(service.queryDataset("employee_dataset", "department", null, "asc")).thenReturn(response);

        mockMvc.perform(get("/api/dataset/employee_dataset/query?groupBy=department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupedRecords.Engineering[0].name").value("John"));
    }
}
