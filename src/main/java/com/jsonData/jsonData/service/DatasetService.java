package com.jsonData.jsonData.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jsonData.jsonData.model.DatasetRecord;

import java.util.List;
import java.util.Map;

public interface DatasetService {
    DatasetRecord insertRecord(String datasetName, JsonNode record);
    List<DatasetRecord> getRecords(String datasetName);
    List<JsonNode> getRecordsSorted(String datasetName, String sortBy, String order);
    Map<String, List<JsonNode>> getRecordsGrouped(String datasetName, String groupBy);
}
