package edu.ucalgary.oop;

//Import java sql methods
import java.sql.*;
import java.util.ArrayList;


public class DBAccess {
    protected Connection dbConnect;
    protected ResultSet inquirerResults;
    protected ResultSet inquiryLogResults;

    //Constructor
    public DBAccess(){

    }

    //Create a connection with your database, use try catch to handle any errors
    public void createConnection(){
        try{
            dbConnect = DriverManager.getConnection("jdbc:postgresql://localhost/ensf380project", "oop", "ucalgary");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Sets the inquirer results variable to the current results from the DB
    public void retrieveInquirers(){
        try{
            Statement selectInquirers = dbConnect.createStatement();
            inquirerResults = selectInquirers.executeQuery("select * from inquirer");
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    //Sets the inquiryLog results variable to the current results from the DB
    public void retrieveInquiryLogResults(){
        try{
            Statement selectInquiryLog = dbConnect.createStatement();
            inquiryLogResults = selectInquiryLog.executeQuery("select * from inquiry_log");
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    //Turns results for any table into a string (Helper function)
    public String stringifyResults(ResultSet results){
        StringBuffer resultStringBuffer = new StringBuffer();
        try{
            //Getting column names
            ArrayList<String> columnNames = new ArrayList<>();
            ResultSetMetaData metaData = results.getMetaData();
            int columnCount = metaData.getColumnCount();
            for(int i = 1;i <= columnCount; i++){
                columnNames.add(metaData.getColumnName(i));
            }

            resultStringBuffer.append("Current results\n");
            while(results.next()){
                for(int j = 0; j < columnCount; j++){
                    resultStringBuffer.append(columnNames.get(j) + ": " + results.getString(columnNames.get(j)) + "\t");
                }
                resultStringBuffer.append("\n");
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return resultStringBuffer.toString();
    }

    public String getInquirers(){
        try{
            this.retrieveInquirers();
            String result = stringifyResults(this.inquirerResults);
            this.inquirerResults.close();
            return result;
        }
        catch(SQLException e){
            e.printStackTrace();
            return "Something went wrong";
        }
    }

    public String getInquiryLog(){
        try{
            this.retrieveInquiryLogResults();
            String result = stringifyResults(this.inquiryLogResults);
            this.inquiryLogResults.close();
            return result;
        }
        catch(SQLException e){
            e.printStackTrace();
            return "Something went wrong";
        }
    }

    //Makes sure that everything is being closed correctly
    public void close(){
        try{
            if(inquirerResults != null){
                inquirerResults.close();
            }
            if(inquiryLogResults != null){
                inquiryLogResults.close();
            }
            dbConnect.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Main for testing
    public static void main(String[] args) {
        DBAccess myJBDC = new DBAccess();
        //1. Create connection
        myJBDC.createConnection();
        myJBDC.retrieveInquiryLogResults();

        String allInquirers = myJBDC.getInquiryLog();
        System.out.println(allInquirers);

        myJBDC.close();
    }
}