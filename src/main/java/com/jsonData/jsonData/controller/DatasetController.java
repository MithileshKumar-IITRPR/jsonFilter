package com.jsonData.jsonData.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.jsonData.jsonData.model.DatasetRecord;
import com.jsonData.jsonData.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dataset/{datasetName}")
public class DatasetController {
    @Autowired
    private DatasetService datasetService;

    @PostMapping("/record")
    public ResponseEntity<Map<String, Object>> insertRecord(@PathVariable String datasetName, @RequestBody JsonNode record){
        DatasetRecord savedRecord = datasetService.insertRecord(datasetName, record);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Record added successfully");
        response.put("dataset", datasetName);
        response.put("recordId", savedRecord.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/query")
    public ResponseEntity<Map<String, Object>> queryDataset(@PathVariable String datasetName,
                                          @RequestParam(required = false) String groupBy,
                                          @RequestParam(required = false) String sortBy,
                                          @RequestParam(required = false, defaultValue = "asc") String order){
        Map<String, Object> response = new HashMap<>();

        if (groupBy != null) {
            response.put("groupedRecords", datasetService.getRecordsGrouped(datasetName, groupBy));
        } else if (sortBy != null) {
            response.put("sortedRecords", datasetService.getRecordsSorted(datasetName, sortBy, order));
        } else {
            response.put("records", datasetService.getRecords(datasetName));
        }

        return ResponseEntity.ok(response);
    }
}
