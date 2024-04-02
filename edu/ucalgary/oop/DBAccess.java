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
        try{
            dbConnect = DriverManager.getConnection("jdbc:postgresql://localhost/ensf380project", "oop", "ucalgary");
        }
        catch(SQLException e){
            e.printStackTrace();
        } 
    }

    //Sets the inquirer results variable to the current results from the DB
    protected void retrieveInquirers(){
        try{
            Statement selectInquirers = dbConnect.createStatement();
            this.inquirerResults = selectInquirers.executeQuery("select * from inquirer");
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    //Sets the inquiryLog results variable to the current results from the DB
    protected void retrieveInquiryLogResults(){
        try{
            Statement selectInquiryLog = dbConnect.createStatement();
            this.inquiryLogResults = selectInquiryLog.executeQuery("select * from inquiry_log");
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    protected void searchInquiryLogResults(String searchTerm){
        try{
            String searchQuery = "SELECT * FROM inquiry_log WHERE LOWER(details) LIKE LOWER(?)";
            PreparedStatement searchStatement = this.dbConnect.prepareStatement(searchQuery);
            searchStatement.setString(1, "%" + searchTerm.trim() + "%");
            this.inquiryLogResults = searchStatement.executeQuery();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        catch(Error e){
            e.printStackTrace();
        }
    }

    //Turns results for any table into a string (Helper function)
    public String stringifyResults(ResultSet results){
        try{
            StringBuffer resultStringBuffer = new StringBuffer();
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
            return resultStringBuffer.toString();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return "";
        }
    }

    public ResultSet getInquirers(){
        this.retrieveInquirers();
        return this.inquirerResults;
    }

    public ResultSet getInquiryLog(){
        this.retrieveInquiryLogResults();
        return this.inquiryLogResults;
    }

    public ResultSet searchInquiryLog(String searchTerm){
        this.searchInquiryLogResults(searchTerm);
        return this.inquiryLogResults;
    }

    //Makes sure that everything is being closed correctly
    public void close(){
        try{
            if(this.inquirerResults != null){
                this.inquirerResults.close();
            }
            if(this.inquiryLogResults != null){
                this.inquiryLogResults.close();
            }
            this.dbConnect.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Main for testing
    public static void main(String[] args) {
        DBAccess myJBDC = new DBAccess();

        ResultSet results = myJBDC.searchInquiryLog("melinda");
        System.out.println(myJBDC.stringifyResults(results));
        myJBDC.close();
    }
}