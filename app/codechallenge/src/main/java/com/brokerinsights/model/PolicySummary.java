package com.brokerinsights.model;

public class PolicySummary {
    private int policyCount;
    private int customerCount;
    private double sumOfInsuredAmount;
    private double averagePolicyDuration;

    public void setPolicyCount(int policyCount) {
        this.policyCount = policyCount;
    }

    public int getPolicyCount() {
        return this.policyCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getCustomerCount() {
        return this.customerCount;
    }

    public void setSumOfInsuredAmount(double sumOfInsuredAmount) {
        this.sumOfInsuredAmount = sumOfInsuredAmount;
    }

    public double getSumOfInsuredAmount() {
        return this.sumOfInsuredAmount;
    }

    public void setAveragePolicyDuration(double averagePolicyDuration) {
        this.averagePolicyDuration = averagePolicyDuration;
    }

    public double getAveragePolicyDuration() {
        return this.averagePolicyDuration;
    }
}