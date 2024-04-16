create database broker_insights;

use broker_insights;

create table broker_policy (
    broker_name varchar(255),
    policy_number varchar(255),
    insured_amount double,
    start_date date,
    end_date date,
    renewal_date date,
    effective_date date,    
    premium varchar(255),
    policy_fee double,
    admin_fee double,
    ipt_amount double,
    commission double,
    business_description text,
  /*business_event enum('New Business', 'Renewal', 'Policy Renewal'),*/
    client_type enum('Corporate', 'Individual', 'unknown'),
    policy_type enum('Auto', 'Health', 'Property', 'unknown'),
    product enum('Auto Coverage', 'Health Insurance', 'Property Insurance', 'unknown'),
    root_policy_ref varchar(255),
    insurer_policy_number varchar(255),
    insurer varchar(255),
    customer varchar(255),
    extra_fields text
);