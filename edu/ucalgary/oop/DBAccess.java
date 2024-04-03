package edu.ucalgary.oop;

//Import java sql methods
import java.sql.*;
import java.util.ArrayList;


public class DBAccess {
    protected Connection dbConnect;
    protected ResultSet inquirerResults;
    protected ResultSet inquiryLogResults;
    protected ResultSet locationResults;

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

    protected ResultSet retrieveAllLocations(){
        try{
            Statement selectLocations = dbConnect.createStatement();
            return selectLocations.executeQuery("SELECT * FROM LOCATION_TABLE");
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
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

    protected void addDisasterVictim(DisasterVictim victim, int locationID){
        try{
            String addVictim = "INSERT INTO DISASTER_VICTIM (fName, lName, dob, age, location_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement addVictimStatement = this.dbConnect.prepareStatement(addVictim, Statement.RETURN_GENERATED_KEYS);
            addVictimStatement.setString(1, victim.getFirstName());
            addVictimStatement.setString(2, victim.getLastName());
            if(victim.getDateOfBirth() == null){
                addVictimStatement.setNull(3, Types.DATE);
                addVictimStatement.setInt(4, victim.getAge());
            }
            else{
                Date sqlDate = Date.valueOf(victim.getDateOfBirth());
                addVictimStatement.setDate(3, sqlDate);
                addVictimStatement.setNull(4, Types.INTEGER);
            }
            addVictimStatement.setInt(5, locationID);
            int affectedRows = addVictimStatement.executeUpdate();
            if (affectedRows > 0){
                ResultSet rs = addVictimStatement.getGeneratedKeys();
                if(rs.next()){
                    int social_id = rs.getInt(1);
                    if(victim.getMedicalRecords().size() != 0){
                        this.addMedicalRecords(victim.getMedicalRecords(), locationID, social_id);
                    }
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    protected boolean validLocationID(int location_id){
        try{
            Statement getIDStatement = this.dbConnect.createStatement();
            ResultSet result = getIDStatement.executeQuery("SELECT MAX(location_id) FROM DISASTER_VICTIM");
            int max_id = -1;
            if(result.next()){
                max_id = result.getInt(1);
            }
            if (location_id < 0 || location_id > max_id){
                return false;
            }
            else{
                return true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private void addMedicalRecords(ArrayList<MedicalRecord> list, int location_id, int social_id){
        try{
            for(int i = 0; i < list.size(); i++){
                String medicalRecord = "INSERT INTO MEDICAL_RECORD (date_of_treatment, treatment_detials, location_id, social_id) VALUES (?, ?, ?, ?)";
                PreparedStatement addMRStatement = this.dbConnect.prepareStatement(medicalRecord);
                Date date = Date.valueOf(list.get(i).getDateOfTreatment());
                addMRStatement.setDate(1, date);
                addMRStatement.setString(2, list.get(i).getTreatmentDetails());
                addMRStatement.setInt(3, location_id);
                addMRStatement.setInt(4, social_id);
                addMRStatement.executeUpdate();
                System.out.println("Success");
            }
        }
        catch(SQLException e){
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