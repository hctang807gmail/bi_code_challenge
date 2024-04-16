package com.brokerinsights.broker.config;

import java.io.Reader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays;

public class BrokerSchema {

    private String configPath;
    private List<String> coreFields = new ArrayList<String>();
    private List<Broker> brokerList = new ArrayList<Broker>();

    public BrokerSchema(String configPath) {
        try {
            this.configPath = configPath;
            Path mappingPath = Paths.get(this.configPath);
            String content = Files.readString(mappingPath);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(content);

            ArrayNode coreFieldsNode = (ArrayNode) json.get("coreFields");
            for (JsonNode node: coreFieldsNode) {
                if (node != null) {
                    coreFields.add(node.asText());
                }
            }

            ArrayNode brokersNode = (ArrayNode) json.get("brokers");
            for (JsonNode node: brokersNode) {
                Broker broker = new Broker();
                String brokerName = "unknown";
                if (node.get("name") != null) {
                    brokerName = node.get("name").asText();
                }
                broker.setName(brokerName);
                ObjectNode fieldsMapping = (ObjectNode) node.get("fieldsMapping");
                for (int i = 0 ; i < coreFields.size() ; i++) {
                    String field = (String)coreFields.get(i);
                    if (fieldsMapping.get(field) != null) {
                        broker.addMapping(fieldsMapping.get(field).asText(), field);
                    }
                }
                brokerList.add(broker);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Broker getBroker(List<String> fields) {
        for (Broker broker: brokerList) {
            if (broker.isSchemaMatch(fields)) {
                return broker;
            }
        }
        return null;
    }
    
}