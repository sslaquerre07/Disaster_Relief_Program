package edu.ucalgary.oop;

//Import java sql methods
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class DBAccess {
    private Connection dbConnect;

    //Constructor
    public DBAccess(){
        try{
            dbConnect = DriverManager.getConnection("jdbc:postgresql://localhost/ensf380project", "oop", "ucalgary");
        }
        catch(SQLException e){
            e.printStackTrace();
        } 
    }

    /*All data retrieval related queries */

    public ResultSet retrieveInquirer(String fname, String lname){
        try{
            String retrieveQuery = "select * from inquirer where firstName = (?) and lastName = (?)";
            PreparedStatement retrieveStatement = this.dbConnect.prepareStatement(retrieveQuery);
            retrieveStatement.setString(1, fname);
            retrieveStatement.setString(2, lname);
            ResultSet result = retrieveStatement.executeQuery();
            return result;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet retrieveLocation(String locationName){
        try{
            String getLocation = "select * from location_table where name = (?)";
            PreparedStatement getLocationStmt = this.dbConnect.prepareStatement(getLocation);
            getLocationStmt.setString(1, locationName);
            ResultSet result = getLocationStmt.executeQuery();
            return result;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    //Sets the inquirer results variable to the current results from the DB
    public ResultSet retrieveInquirers(){
        try{
            Statement selectInquirers = dbConnect.createStatement();
            ResultSet result = selectInquirers.executeQuery("select * from inquirer");
            return result;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet searchDisasterVictim(String fname){
        try{
            String searchQuery = "select * from disaster_victim where lower(fname) like lower(?)";
            PreparedStatement searchStatement = this.dbConnect.prepareStatement(searchQuery);
            searchStatement.setString(1, '%' + fname + '%');
            ResultSet result = searchStatement.executeQuery();
            return result;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet searchDisasterVictim(String fname, String lname){
        try{
            String searchQuery = "select * from disaster_victim where (lower(fname) like lower(?)) and (lower(lname) like lower(?))";
            PreparedStatement searchStatement = this.dbConnect.prepareStatement(searchQuery);
            searchStatement.setString(1, '%' + fname + '%');
            searchStatement.setString(2, '%' + lname + '%');
            ResultSet result = searchStatement.executeQuery();
            return result;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet retrieveDisasterVictim(String fname, String lname){
        try{
            String searchQuery = "select * from disaster_victim where fname = (?) and lname = (?)";
            PreparedStatement searchStatement = this.dbConnect.prepareStatement(searchQuery);
            searchStatement.setString(1, fname);
            searchStatement.setString(2, lname);
            ResultSet result = searchStatement.executeQuery();
            return result;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    //Sets the inquiryLog results variable to the current results from the DB
    public ResultSet retrieveInquiryLogResults(){
        try{
            Statement selectInquiryLog = dbConnect.createStatement();
            ResultSet result = selectInquiryLog.executeQuery("select * from inquiry_log");
            return result;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    protected ResultSet retrieveAllLocations(){
        try{
            Statement selectLocations = dbConnect.createStatement();
            ResultSet result =selectLocations.executeQuery("select * from location_table");
            return result;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet searchInquiryLogResults(String searchTerm){
        try{
            String searchQuery = "SELECT * FROM inquiry_log WHERE LOWER(details) LIKE LOWER(?)";
            PreparedStatement searchStatement = this.dbConnect.prepareStatement(searchQuery);
            searchStatement.setString(1, "%" + searchTerm.trim() + "%");
            ResultSet result = searchStatement.executeQuery();
            return result;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet retrieveAllDisasterVictims(){
        try{
            Statement victimRetrieval = this.dbConnect.createStatement();
            ResultSet result = victimRetrieval.executeQuery("select * from disaster_victim");
            return result;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }


    /*All data insertion related queries */
    public int addInquirer(String fname, String lname, String phone){
        try{
            String addQuery = "INSERT INTO INQUIRER (firstName, lastName, phoneNumber) VALUES (?, ?, ?)";
            PreparedStatement addStatement = this.dbConnect.prepareStatement(addQuery);
            addStatement.setString(1, fname);
            addStatement.setString(2, lname);
            addStatement.setString(3, phone);
            return addStatement.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return -1;
        }
    }

    public int addInquiry(int social_id, int location_id, int inquirer_id, Inquiry inquiry){
        try{
            String addQuery = "INSERT INTO INQUIRY_LOG (inquirer, callDate, details, location_id, social_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement addStatement = this.dbConnect.prepareStatement(addQuery);
            addStatement.setInt(1, inquirer_id);
            DateFormat dateFormat = (new SimpleDateFormat("yyyy-MM-dd"));
            java.util.Date date = dateFormat.parse(inquiry.getDateOfInquiry());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            addStatement.setDate(2, sqlDate);
            addStatement.setString(3, inquiry.getInfoProvided());
            addStatement.setInt(4, location_id);
            if(social_id != -1){
                addStatement.setInt(5, social_id);
            }
            else{
                addStatement.setNull(5, Types.INTEGER);
            }
            return addStatement.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return -1;
        }
        catch(ParseException ex){
            ex.printStackTrace();
            return -1;
        }
    }

    public void addDisasterVictim(DisasterVictim victim, int locationID, ArrayList<DisasterVictim> relatives){
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
                    if(victim.getFamilyConnections().size() != 0){
                        this.addFamilyRelations(victim, relatives);
                    }
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    //Helper function for GUI
    public boolean validLocationID(int location_id){
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

    public int addLocation(Location location){
        try{
            String addLocation = "INSERT INTO LOCATION_TABLE (name, address) VALUES (?, ?)";
            PreparedStatement addLocationQuery = this.dbConnect.prepareStatement(addLocation);
            addLocationQuery.setString(1, location.getName());
            addLocationQuery.setString(2, location.getAddress());
            return addLocationQuery.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return -1;
        }
    }

    public int addMedicalRecords(ArrayList<MedicalRecord> list, int location_id, int social_id){
        try{
            int recordsAdded = 0;
            for(int i = 0; i < list.size(); i++){
                String medicalRecord = "INSERT INTO MEDICAL_RECORD (date_of_treatment, treatment_detials, location_id, social_id) VALUES (?, ?, ?, ?)";
                PreparedStatement addMRStatement = this.dbConnect.prepareStatement(medicalRecord);
                Date date = Date.valueOf(list.get(i).getDateOfTreatment());
                addMRStatement.setDate(1, date);
                addMRStatement.setString(2, list.get(i).getTreatmentDetails());
                try{
                    ResultSet locationInfo = this.retrieveLocation(list.get(i).getLocation().getName());
                    if(locationInfo.next()){
                        addMRStatement.setInt(3, locationInfo.getInt("location_id"));
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
                addMRStatement.setInt(4, social_id);
                recordsAdded++;
            }
            return recordsAdded;
        }
        catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public int addFamilyRelations(DisasterVictim victim, ArrayList<DisasterVictim> relatives){
        //Getting information for the victim before the loop
        try{
            int rowsAdded = 0;
            ResultSet person1Info = this.retrieveDisasterVictim(victim.getFirstName(), victim.getLastName());
            int person1ID = 0;
            if(person1Info.next()){
                person1ID = person1Info.getInt("social_id");
            }

            for(int i = 0; i < relatives.size(); i++){
                String insertQuery = "INSERT INTO FAMILY_RELATIONS (person1ID, person2ID, relationship) VALUES (?, ?, ?)";
                PreparedStatement insertStatement = this.dbConnect.prepareStatement(insertQuery);
                ResultSet person2Info = this.retrieveDisasterVictim(relatives.get(i).getFirstName(), relatives.get(i).getLastName());
                int person2ID = 0;
                if(person2Info.next()){
                    person2ID = person2Info.getInt("social_id");
                }
                insertStatement.setInt(1, person1ID);
                insertStatement.setInt(2, person2ID);
                insertStatement.setString(3, victim.getFamilyConnections().get(i).getRelationshipTo());
                insertStatement.executeUpdate();
                rowsAdded++;
            }
            return rowsAdded;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return -1;
        }
    }

    /*Additional functions */

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

    //Makes sure that everything is being closed correctly
    public void close(){
        try{
            this.dbConnect.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Main for testing
    public static void main(String[] args) {
        DBAccess myJBDC = new DBAccess();

        ResultSet results = myJBDC.searchInquiryLogResults("melinda");
        System.out.println(myJBDC.stringifyResults(results));
        myJBDC.close();
    }
}