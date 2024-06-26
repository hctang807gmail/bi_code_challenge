package com.brokerinsights;

import com.brokerinsights.broker.IDataReader;
import com.brokerinsights.broker.CsvReader;

import com.brokerinsights.broker.config.BrokerSchema;

import com.brokerinsights.operation.IImportData;
import com.brokerinsights.operation.IQueryInfo;
import com.brokerinsights.operation.MysqlDataSource;

import com.brokerinsights.model.PolicySummary;
import com.brokerinsights.model.PolicyRecord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Date;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CodeChallenge 
{
    static String dataMappingPath = "/broker_insights/config/dataMapping.json";
    static String uploadFolderPath = "/broker_insights/upload";
    public static void main( String[] args )
    {
        try {
            Path mappingPath = Paths.get(dataMappingPath);
            String mappingJson = Files.readString(mappingPath);
            BrokerSchema brokerSchema = new BrokerSchema(mappingJson);
            IDataReader reader = new CsvReader(brokerSchema);
            MysqlDataSource mysqlDataSource = new MysqlDataSource(reader, uploadFolderPath);
            IImportData importData = mysqlDataSource;
            IQueryInfo queryInfo = mysqlDataSource;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            boolean quit = false;
            while (quit == false) {
                try {
                    printMainMenu();
                    String input = br.readLine();
                    switch (input) {
                        case "1":
                            clearnConsole();
                            int total = importData.importData();
                            printImportResult(total);                            
                            break;
                        case "2":
                            PolicySummary policySummary = queryInfo.getPolicySummary();
                            printPolicySummary(policySummary);
                            break;
                        case "3":
                            System.out.print("Enter broker name [broker1, broker2]: ");
                            String brokerName = br.readLine();
                            List<PolicyRecord> list = queryInfo.getPolicyByBroker(brokerName);
                            printPolicyList(brokerName, list);
                            break;
                        case "4":
                            quit = true;
                            break;
                        default:
                            System.out.println("Invalid option.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("See you.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertDateToString(Date date) {
        if (date != null) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format(date);
        }
        return "N/A";
    }

    private static void printImportResult(int total) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(String.format("%d records imported.", total));
        System.out.print("Press enter to continue...");
        br.readLine();
    }

    private static void printPolicyList(String brokerName, List<PolicyRecord> list) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean printEnd = false;
        int pageNo = 1;
        if (list.size() == 0) {
            System.out.println(String.format("No policy for broker:[%s]", brokerName));
        } else {
            for (int i = 0 ; i < list.size() ; i++) {
                if ((i+1) % 10 == 1) {
                    printEnd = false;
                    clearnConsole();
                    System.out.println(String.format("Policy by broker:[%s], pageNo:[%d]", brokerName, pageNo++));
                }
                PolicyRecord record = list.get(i);
                System.out.println(String.format("PolicyNumber:[%10s], Customer:[%10s], InsuredAmount:[%15s], StartDate:[%10s], EndDate:[%10s]", record.getPolicyNumber(), record.getCustomer(), record.getInsuredAmount(), convertDateToString(record.getStartDate()), convertDateToString(record.getEndDate())));
                if ((i+1) % 10 == 0) {
                    System.out.print("Press enter to continue...");
                    br.readLine();
                    printEnd = true;
                }
            }
        }
        if (printEnd == false) {
            System.out.print("Press enter to continue...");
            br.readLine();
        }
    }

    private static void printPolicySummary(PolicySummary policySummary) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        clearnConsole();
        System.out.println(String.format("Number of active policy:                              [%15d]", policySummary.getPolicyCount()));
        System.out.println(String.format("Number of customer with active policy:                [%15d]", policySummary.getCustomerCount()));
        System.out.println(String.format("Sum of insured amount:                                [%15.2f]", policySummary.getSumOfInsuredAmount()));
        System.out.println(String.format("Average policy duration:                              [%15.2f]", policySummary.getAveragePolicyDuration()));
        System.out.print("Press enter to continue...");
        br.readLine();
    }

    private static void clearnConsole() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }

    private static void printMainMenu() throws IOException {
        clearnConsole();
        System.out.println(String.format("1. Import from upload folder [%s]", uploadFolderPath));
        System.out.println(String.format("2. Display policy summary"));
        System.out.println(String.format("3. Display policy by broker"));
        System.out.println(String.format("4. Quit"));
        System.out.print("> ");
    }
}
