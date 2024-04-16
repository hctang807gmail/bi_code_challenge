package com.brokerinsights.broker;

import java.io.Reader;
import java.io.FileReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;

import com.brokerinsights.broker.config.Broker;
import com.brokerinsights.broker.config.BrokerSchema;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import com.brokerinsights.model.PolicyRecord;

public class CsvReader implements IDataReader {

    private BrokerSchema brokerSchema;

    public CsvReader(BrokerSchema brokerSchema) {
        this.brokerSchema = brokerSchema;
    }

    public List<PolicyRecord> readData(String filePath) throws Exception {
        List<PolicyRecord> result = new ArrayList<PolicyRecord>();
        Reader in = new FileReader(filePath);
        CSVParser parser = new CSVParser(in, CSVFormat.EXCEL);
        List<CSVRecord> records = parser.getRecords();
        CSVRecord headers = records.get(0);
        Broker broker = brokerSchema.getBroker(headers.toList());
        for (int i = 1 ; i < records.size() ; i++) {
            PolicyRecord policyRecord = new PolicyRecord();
            policyRecord.setBrokerName(broker.getName());
            CSVRecord record = records.get(i);
            for (int j = 0 ; j < headers.size() ; j++) {
                String key = broker.getMapping(headers.get(j));
                if (key == null) {
                    key = headers.get(j);
                }
                switch(key) {
                    case "policy_number":
                        policyRecord.setPolicyNumber(record.get(j));
                        break;
                    case "insured_amount":
                        policyRecord.setInsuredAmount(record.get(j));
                        break;
                    case "start_date":
                        policyRecord.setStartDate(record.get(j));
                        break;
                    case "end_date":
                        policyRecord.setEndDate(record.get(j));
                        break;
                    case "renewal_date":
                        policyRecord.setRenewalDate(record.get(j));
                        break;
                    case "effective_date":
                        policyRecord.setEffectiveDate(record.get(j));
                        break;
                    case "premium":
                        policyRecord.setPremium(record.get(j));
                        break;
                    case "policy_fee":
                        policyRecord.setPolicyFee(record.get(j));
                        break;
                    case "admin_fee":
                        policyRecord.setAdminFee(record.get(j));
                        break;
                    case "ipt_amount":
                        policyRecord.setIptAmount(record.get(j));
                        break;
                    case "commission":
                        policyRecord.setCommission(record.get(j));
                        break;
                    case "business_description":
                        policyRecord.setBusinessDescription(record.get(j));
                        break;
                    case "client_type":
                        policyRecord.setClientType(record.get(j));
                        break;
                    case "policy_type":
                        policyRecord.setPolicyType(record.get(j));
                        break;
                    case "product":
                        policyRecord.setProduct(record.get(j));
                        break;
                    case "root_policy_ref":
                        policyRecord.setRootPolicyRef(record.get(j));
                        break;
                    case "insurer_policy_number":
                        policyRecord.setInsurerPolicyNumber(record.get(j));
                        break;
                    case "insurer":
                        policyRecord.setInsurer(record.get(j));
                        break;
                    case "customer":
                        policyRecord.setCustomer(record.get(j));
                        break;
                    default:
                        policyRecord.addExtraField(key, record.get(j));
                }
            }
            result.add(policyRecord);
        }
        return result;
    }
}