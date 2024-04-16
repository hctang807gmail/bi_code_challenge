package com.brokerinsights.operation;

import java.util.List;
import com.brokerinsights.model.PolicyRecord;
import com.brokerinsights.model.PolicySummary;

public interface IQueryInfo {
    public List<PolicyRecord> getPolicyByBroker(String brokerName);
    public PolicySummary getPolicySummary();
}