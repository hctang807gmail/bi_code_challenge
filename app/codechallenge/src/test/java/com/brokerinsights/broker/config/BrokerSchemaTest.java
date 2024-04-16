package com.brokerinsights.broker.config;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

public class BrokerSchemaTest 
{
    @Test
    public void verifyGetBroker() {
        String content = "{\"coreFields\": [\"policy_number\", \"insured_amount\"], \"brokers\":["
        + "{\"name\": \"broker1\", \"fieldsMapping\": {\"policy_number\": \"PolicyNumber\",\"insured_amount\": \"InsuredAmount\"}},"
        + "{\"name\": \"broker2\", \"fieldsMapping\": {\"policy_number\": \"PolicyRef\",\"insured_amount\": \"CoverageAmount\"}}"
        + "]}";
        BrokerSchema brokerSchema = new BrokerSchema(content);
        List<String> fields1 = new ArrayList<String>();
        fields1.add("PolicyNumber");
        fields1.add("InsuredAmount");
        List<String> fields2 = new ArrayList<String>();
        fields2.add("PolicyRef");
        fields2.add("CoverageAmount");
        List<String> fields3 = new ArrayList<String>();
        fields3.add("Test1");
        fields3.add("Test2");
        Broker broker1 = brokerSchema.getBroker(fields1);
        Broker broker2 = brokerSchema.getBroker(fields2);
        
        assertEquals("verifyGetBroker", "broker1", broker1.getName());
        assertEquals("verifyGetBroker", "broker2", broker2.getName());
        assertNull(brokerSchema.getBroker(fields3));
   }
}