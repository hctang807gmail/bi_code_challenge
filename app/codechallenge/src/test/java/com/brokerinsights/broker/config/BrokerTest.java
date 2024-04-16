package com.brokerinsights.broker.config;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;


public class BrokerTest 
{
    @Test
    public void verifyIsSchemaMatch() {
        Broker broker = new Broker();
        broker.addMapping("source1", "target1");
        broker.addMapping("source2", "target2");
        broker.addMapping("source3", "target3");

        List<String> fields1 = new ArrayList<String>();
        fields1.add("source1");
        fields1.add("source2");
        fields1.add("source3");
        assertTrue(broker.isSchemaMatch(fields1));

        List<String> fields2 = new ArrayList<String>();
        fields2.add("source1");
        fields2.add("source2");
        fields2.add("source4");
        assertFalse(broker.isSchemaMatch(fields2));
    }

    @Test
    public void verifyGetMapping() {
        Broker broker = new Broker();
        broker.addMapping("source1", "target1");
        broker.addMapping("source2", "target2");
        broker.addMapping("source3", "target3");

        assertEquals("verifyGetMapping", "target1", broker.getMapping("source1"));
    }
}