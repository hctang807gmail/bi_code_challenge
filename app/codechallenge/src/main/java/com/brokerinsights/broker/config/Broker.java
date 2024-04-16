package com.brokerinsights.broker.config;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Broker {
    private String name;
    private HashMap<String, String> coreFieldsMapping = new HashMap<String, String>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addMapping(String key, String value) {
        coreFieldsMapping.put(key, value);
    }

    public String getMapping(String key) {
        return coreFieldsMapping.get(key);
    }

    public boolean isSchemaMatch(List<String> fields) {
        for (String key: coreFieldsMapping.keySet()) {
            if (fields.contains(key) == false) {
                return false;
            }
        }
        return true;
    }
}