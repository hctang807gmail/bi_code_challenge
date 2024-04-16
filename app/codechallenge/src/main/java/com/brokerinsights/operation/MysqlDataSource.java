package com.brokerinsights.operation;

import java.io.File;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import com.brokerinsights.broker.IDataReader;
import com.brokerinsights.model.PolicyRecord;
import com.brokerinsights.model.PolicySummary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MysqlDataSource implements IImportData, IQueryInfo {
    private IDataReader reader;
    private String uploadPath;
    private String url = "jdbc:mysql://localhost:3306/broker_insights";
    private String username = "broker_insights";
    private String password = "passw0rd";
    private String insertSql = "insert into broker_policy (broker_name, policy_number, insured_amount, start_date, end_date, renewal_date, effective_date, " +
                    "premium, policy_fee, admin_fee, ipt_amount, commission, business_description, client_type, policy_type, product, root_policy_ref, " + 
                    "insurer_policy_number, insurer, customer, extra_fields) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private String queryBrokerSql = "select broker_name, policy_number, insured_amount, DATE_FORMAT(start_date, \"%d/%m/%Y\") as start_date, DATE_FORMAT(end_date, \"%d/%m/%Y\") as end_date, DATE_FORMAT(renewal_date, \"%d/%m/%Y\") as renewal_date, DATE_FORMAT(effective_date, \"%d/%m/%Y\") as effective_date, " +
                    "premium, policy_fee, admin_fee, ipt_amount, commission, business_description, client_type, policy_type, product, root_policy_ref, " + 
                    "insurer_policy_number, insurer, customer, extra_fields from broker_policy where broker_name = ?;";

    private String queryActivePolicyCount = "select count(*) from broker_policy where start_date < now() and renewal_date > now();";
    private String queryActiveCustomerCount = "select count(distinct(customer)) from broker_policy where start_date < now() and renewal_date > now();";
    private String querySumOfActiveInsuredAmount = "select sum(insured_amount) from broker_policy where start_date < now() and renewal_date > now();";
    private String queryAveragePolicyDuration = "select avg(timestampdiff(DAY, start_date, renewal_date)) from broker_policy where start_date < now() and renewal_date > now();";
    private String queryAveragePolicyDurationAsOfToday = "select avg(timestampdiff(DAY, start_date, now())) from broker_policy where start_date < now() and renewal_date > now();";


    public MysqlDataSource(IDataReader reader, String uploadPath) {
        this.reader = reader;
        this.uploadPath = uploadPath;
    }

    private JsonNode convertMapToJson(HashMap<String, String> map) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode json = objectMapper.createObjectNode();
        for (String key: map.keySet()) {
            json.put(key, map.get(key));
        }
        return (JsonNode)json;
    }

    private java.sql.Date getSqlDate(java.util.Date date) {
        if (date != null) {
            return new java.sql.Date(date.getTime());
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public int importData() {
        File imported = new File(this.uploadPath + "/imported");
        imported.mkdirs();
        File folder = new File(this.uploadPath);
        File[] listOfFiles = folder.listFiles();
        int totalCount = 0;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    int count = 0;
                    Connection connection = getConnection();
                    connection.setAutoCommit(false);
                    List<PolicyRecord> records = this.reader.readData(file.getPath());
                    String extraFields = "";
                    for (PolicyRecord record: records) {
                        JsonNode json = convertMapToJson(record.getExtraFields());
                        extraFields = ((JsonNode)json).toString();
                        PreparedStatement stmt = connection.prepareStatement(insertSql);
                        stmt.setString(1, record.getBrokerName());
                        stmt.setString(2, record.getPolicyNumber());
                        stmt.setDouble(3, record.getInsuredAmount());
                        stmt.setDate(4, getSqlDate(record.getStartDate()));
                        stmt.setDate(5, getSqlDate(record.getEndDate()));
                        stmt.setDate(6, getSqlDate(record.getRenewalDate()));
                        stmt.setDate(7, getSqlDate(record.getEffectiveDate()));
                        stmt.setString(8, record.getPremium());
                        stmt.setDouble(9, record.getPolicyFee());
                        stmt.setDouble(10, record.getAdminFee());
                        stmt.setDouble(11, record.getIptAmount());
                        stmt.setDouble(12, record.getCommission());
                        stmt.setString(13, record.getBusinessDescription());
                        stmt.setString(14, record.getClientType());
                        stmt.setString(15, record.getPolicyType());
                        stmt.setString(16, record.getProduct());
                        stmt.setString(17, record.getRootPolicyRef());
                        stmt.setString(18, record.getInsurerPolicyNumber());
                        stmt.setString(19, record.getInsurer());
                        stmt.setString(20, record.getCustomer());
                        stmt.setString(21, extraFields);
                        stmt.executeUpdate();
                        count++;
                    }
                    connection.commit();
                    connection.setAutoCommit(true);
                    connection.close();
                    totalCount += count;
                    file.renameTo(new File(this.uploadPath + "/imported/" + file.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return totalCount;
    }

    public List<PolicyRecord> getPolicyByBroker(String brokerName) {
        List<PolicyRecord> result = new ArrayList<PolicyRecord>();
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(queryBrokerSql);
            stmt.setString(1, brokerName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PolicyRecord record = new PolicyRecord();
                record.setBrokerName(rs.getString("broker_name"));
                record.setPolicyNumber(rs.getString("policy_number"));
                record.setInsuredAmount(rs.getString("insured_amount"));
                record.setStartDate(rs.getString("start_date"));
                record.setEndDate(rs.getString("end_date"));
                record.setRenewalDate(rs.getString("renewal_date"));
                record.setEffectiveDate(rs.getString("effective_date"));
                record.setPremium(rs.getString("premium"));
                record.setPolicyFee(rs.getString("policy_fee"));
                record.setAdminFee(rs.getString("admin_fee"));
                record.setIptAmount(rs.getString("ipt_amount"));
                record.setCommission(rs.getString("commission"));
                record.setBusinessDescription(rs.getString("business_description"));
                record.setClientType(rs.getString("client_type"));
                record.setPolicyType(rs.getString("policy_type"));
                record.setProduct(rs.getString("product"));
                record.setRootPolicyRef(rs.getString("root_policy_ref"));
                record.setInsurerPolicyNumber(rs.getString("insurer_policy_number"));
                record.setInsurer(rs.getString("insurer"));
                record.setCustomer(rs.getString("customer"));
                result.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public PolicySummary getPolicySummary() {
        PolicySummary policySummary = new PolicySummary();
        policySummary.setPolicyCount(Integer.parseInt(getSingleDataResult(queryActivePolicyCount)));
        policySummary.setCustomerCount(Integer.parseInt(getSingleDataResult(queryActiveCustomerCount)));
        policySummary.setSumOfInsuredAmount(Double.parseDouble(getSingleDataResult(querySumOfActiveInsuredAmount)));
        policySummary.setAveragePolicyDuration(Double.parseDouble(getSingleDataResult(queryAveragePolicyDuration)));
        return policySummary;
    }

    private String getSingleDataResult(String sql) {
        String result = null;
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}