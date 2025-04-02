package com.jsonData.jsonData.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsonData.jsonData.model.DatasetRecord;
import com.jsonData.jsonData.repository.DatasetRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatasetServiceImpl implements DatasetService{

    private final DatasetRepository datasetRepository;
    private final ObjectMapper objectMapper;

    @Override
    public DatasetRecord insertRecord(String datasetName, JsonNode record) {
        DatasetRecord datasetRecord = new DatasetRecord(null, datasetName, record);
        return datasetRepository.save(datasetRecord);
    }

    @Override
    public List<DatasetRecord> getRecords(String datasetName) {
        return datasetRepository.findByDatasetName(datasetName);
    }

    @Override
    public List<JsonNode> getRecordsSorted(String datasetName, String sortBy, String order) {
        List<DatasetRecord> datasetRecords = datasetRepository.findByDatasetName(datasetName);
        List<JsonNode> jsonRecords = new ArrayList<>();

        for (DatasetRecord record : datasetRecords) {
            jsonRecords.add(parseJson(record.getRecord().toString()));
        }

        jsonRecords.sort((a, b) -> {
            String val1 = a.has(sortBy) ? a.get(sortBy).asText() : "";
            String val2 = b.has(sortBy) ? b.get(sortBy).asText() : "";
            return "desc".equalsIgnoreCase(order) ? val2.compareTo(val1) : val1.compareTo(val2);
        });

        return jsonRecords;
    }

    @Override
    public Map<String, List<JsonNode>> getRecordsGrouped(String datasetName, String groupBy) {
        List<DatasetRecord> datasetRecords = datasetRepository.findByDatasetName(datasetName);
        Map<String, List<JsonNode>> groupedRecords = new HashMap<>();

        for (DatasetRecord record : datasetRecords) {
            JsonNode jsonNode = parseJson(record.getRecord().toString());
            String key = jsonNode.has(groupBy) ? jsonNode.get(groupBy).asText() : "UNKNOWN";

            if (!groupedRecords.containsKey(key)) groupedRecords.put(key, new ArrayList<>());

            groupedRecords.get(key).add(jsonNode);
        }

        return groupedRecords;
    }

    private JsonNode parseJson(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON: " + e.getMessage(), e);
        }
    }
}
