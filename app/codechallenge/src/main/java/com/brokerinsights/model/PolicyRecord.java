package com.brokerinsights.model;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.HashMap;

public class PolicyRecord {
    private String brokerName;
    private String policyNumber;
    private double insuredAmount;
    private Date startDate;
    private Date endDate;
    private Date renewalDate;
    private Date effectiveDate;
    private String premium;
    private double policyFee;
    private double adminFee;
    private double iptAmount;
    private double commission;
    private String businessDescription;
//    private String businessEvent;
    private String clientType;
    private String policyType;
    private String product;
    private String rootPolicyRef;
    private String insurerPolicyNumber;
    private String insurer;
    private String customer;

    private HashMap<String, String> extraFields = new HashMap<String, String>();

    private Date convertStringToDate(String dateInString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.parse(dateInString);
        } catch (Exception e) {
            return null;
        } 
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerName() {
        return this.brokerName;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPolicyNumber() {
        return this.policyNumber;
    }

    public void setInsuredAmount(String insuredAmount) {
        this.insuredAmount = Double.parseDouble(insuredAmount);
    }

    public double getInsuredAmount() {
        return this.insuredAmount;
    }

    public void setStartDate(String startDate) {
        this.startDate = convertStringToDate(startDate);
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = convertStringToDate(endDate);
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setRenewalDate(String renewalDate) {
        this.renewalDate = convertStringToDate(renewalDate);
    }

    public Date getRenewalDate() {
        return this.renewalDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = convertStringToDate(effectiveDate);
    }

    public Date getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getPremium() {
        return this.premium;
    }

    public void setPolicyFee(String policyFee) {
        this.policyFee = Double.parseDouble(policyFee);
    }

    public double getPolicyFee() {
        return this.policyFee;
    }

    public void setAdminFee(String adminFee) {
        this.adminFee = Double.parseDouble(adminFee);
    }

    public double getAdminFee() {
        return this.adminFee;
    }

    public void setIptAmount(String iptAmount) {
        this.iptAmount = Double.parseDouble(iptAmount);
    }

    public double getIptAmount() {
        return this.iptAmount;
    }

    public void setCommission(String commission) {
        this.commission = Double.parseDouble(commission);
    }

    public double getCommission() {
        return this.commission;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public String getBusinessDescription() {
        return this.businessDescription;
    }

    public void setClientType(String clientType) {
        String value = "unknown";
        switch (clientType) {
            case "Corporate":
                value = "Corporate";
                break;
            case "Individual":
                value = "Individual";
                break;
        }
        this.clientType = value;
    }

    public String getClientType() {
        return this.clientType;
    }

    public void setPolicyType(String policyType) {
        String value = "unknown";
        switch (policyType) {
            case "Auto":
                value = "Auto";
                break;
            case "Health":
                value = "Health";
                break;
            case "Property":
                value = "Property";
                break;
        }
        this.policyType = value;
    }

    public String getPolicyType() {
        return this.policyType;
    }

    public void setProduct(String product) {
        String value = "unknown";
        switch (product) {
            case "Auto Coverage":
            case "Auto Protection":
            case "Vehicle Shield":
            case "Vehicle Secure":
                value = "Auto Coverage";
                break;
            case "Health Insurance":
            case "Health Protection":
            case "Life Care":
            case "Life Secure":                
                value = "Health Insurance";
                break;
            case "Property Insurance":
            case "Property Coverage":
            case "Home Secure":
            case "Home Protect":
                value = "Property Insurance";
                break;
        }
        this.product = value;
    }

    public String getProduct() {
        return this.product;
    }

    public void setRootPolicyRef(String rootPolicyRef) {
        this.rootPolicyRef = rootPolicyRef;
    }

    public String getRootPolicyRef() {
        return this.rootPolicyRef;
    }

    public void setInsurerPolicyNumber(String insurerPolicyNumber) {
        this.insurerPolicyNumber = insurerPolicyNumber;
    }

    public String getInsurerPolicyNumber() {
        return this.insurerPolicyNumber;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public String getInsurer() {
        return this.insurer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return this.customer;
    }

    public void addExtraField(String key, String value) {
        this.extraFields.put(key, value);
    }

    public HashMap<String, String> getExtraFields() {
        return this.extraFields;
    }

}