package com.brokerinsights.broker;

import java.util.List;
import com.brokerinsights.model.PolicyRecord;

public interface IDataReader {
    public List<PolicyRecord> readData(String filePath) throws Exception;
}