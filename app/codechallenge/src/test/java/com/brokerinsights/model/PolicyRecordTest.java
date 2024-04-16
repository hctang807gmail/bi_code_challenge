package com.brokerinsights.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.text.ParseException;

/**
 * Unit test for simple App.
 */
public class PolicyRecordTest 
{
    @Test
    public void verifyDateRelatedFields() throws ParseException
    {
        String [] dateString = {"20/3/2023", "01/10/2024"};
        PolicyRecord record = new PolicyRecord();
        for (int i = 0 ; i < dateString.length ; i++) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(dateString[i]);
            record.setStartDate(dateString[i]);
            assertEquals("verifyDateRelatedFields", (Object)date, (Object)record.getStartDate());
            record.setEndDate(dateString[i]);
            assertEquals("verifyDateRelatedFields", (Object)date, (Object)record.getEndDate());
            record.setRenewalDate(dateString[i]);
            assertEquals("verifyDateRelatedFields", (Object)date, (Object)record.getRenewalDate());
            record.setEffectiveDate(dateString[i]);
            assertEquals("verifyDateRelatedFields", (Object)date, (Object)record.getEffectiveDate());
        }
    }

    @Test
    public void verifyClientType() throws ParseException
    {
        String CORPORATE = "Corporate";
        String INDIVIDUAL = "Individual";
        String UNKNOWN = "unknown";
        PolicyRecord record = new PolicyRecord();
        record.setClientType(CORPORATE);
        assertEquals("verifyClientType", CORPORATE, record.getClientType());
        record.setClientType(INDIVIDUAL);
        assertEquals("verifyClientType", INDIVIDUAL, record.getClientType());
        record.setClientType("randomString");
        assertEquals("verifyClientType", UNKNOWN, record.getClientType());
    }

    @Test
    public void verifyPolicyType() throws ParseException
    {
        String AUTO = "Auto";
        String HEALTH = "Health";
        String PROPERTY = "Property";
        String UNKNOWN = "unknown";
        PolicyRecord record = new PolicyRecord();
        record.setPolicyType(AUTO);
        assertEquals("verifyPolicyType", AUTO, record.getPolicyType());
        record.setPolicyType(HEALTH);
        assertEquals("verifyPolicyType", HEALTH, record.getPolicyType());
        record.setPolicyType(PROPERTY);
        assertEquals("verifyPolicyType", PROPERTY, record.getPolicyType());
        record.setPolicyType("randomString");
        assertEquals("verifyPolicyType", UNKNOWN, record.getPolicyType());
    }

    @Test
    public void verifyProduct() throws ParseException
    {
        String AUTO = "Auto Coverage";
        String HEALTH = "Health Insurance";
        String PROPERTY = "Property Insurance";
        String UNKNOWN = "unknown";
        PolicyRecord record = new PolicyRecord();
        record.setProduct("Auto Coverage");
        assertEquals("verifyProduct", AUTO, record.getProduct());
        record.setProduct("Auto Protection");
        assertEquals("verifyProduct", AUTO, record.getProduct());
        record.setProduct("Vehicle Shield");
        assertEquals("verifyProduct", AUTO, record.getProduct());
        record.setProduct("Vehicle Secure");
        assertEquals("verifyProduct", AUTO, record.getProduct());

        record.setProduct("Health Insurance");
        assertEquals("verifyProduct", HEALTH, record.getProduct());
        record.setProduct("Health Protection");
        assertEquals("verifyProduct", HEALTH, record.getProduct());
        record.setProduct("Life Care");
        assertEquals("verifyProduct", HEALTH, record.getProduct());
        record.setProduct("Life Secure");
        assertEquals("verifyProduct", HEALTH, record.getProduct());

        record.setProduct("Property Insurance");
        assertEquals("verifyProduct", PROPERTY, record.getProduct());
        record.setProduct("Property Coverage");
        assertEquals("verifyProduct", PROPERTY, record.getProduct());
        record.setProduct("Home Secure");
        assertEquals("verifyProduct", PROPERTY, record.getProduct());
        record.setProduct("Home Protect");
        assertEquals("verifyProduct", PROPERTY, record.getProduct());

        record.setProduct("randomString");
        assertEquals("verifyProduct", UNKNOWN, record.getProduct());
    }

    @Test
    public void verifyOtherFields() throws ParseException
    {
        PolicyRecord record = new PolicyRecord();
        String stringValue = "123.45";
        double doubleValue = 123.45;
        record.setBrokerName(stringValue);
        assertEquals("verifyOtherFields", stringValue, record.getBrokerName());
        record.setPolicyNumber(stringValue);
        assertEquals("verifyOtherFields", stringValue, record.getPolicyNumber());
        record.setPremium(stringValue);
        assertEquals("verifyOtherFields", stringValue, record.getPremium());
        record.setInsuredAmount(stringValue);
        assertEquals(doubleValue, record.getInsuredAmount(), 0);
        record.setPolicyFee(stringValue);
        assertEquals(doubleValue, record.getPolicyFee(), 0);
        record.setAdminFee(stringValue);
        assertEquals(doubleValue, record.getAdminFee(), 0);
        record.setBusinessDescription(stringValue);
        assertEquals("verifyOtherFields", stringValue, record.getBusinessDescription());
        record.setRootPolicyRef(stringValue);
        assertEquals("verifyOtherFields", stringValue, record.getRootPolicyRef());
        record.setInsurerPolicyNumber(stringValue);
        assertEquals("verifyOtherFields", stringValue, record.getInsurerPolicyNumber());
        record.setInsurer(stringValue);
        assertEquals("verifyOtherFields", stringValue, record.getInsurer());
        record.setCustomer(stringValue);
        assertEquals("verifyOtherFields", stringValue, record.getCustomer());
    }
}