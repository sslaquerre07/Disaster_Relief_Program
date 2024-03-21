package edu.ucalgary.oop;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class ReliefService extends DBAccess{
    private Inquirer inquirer;
    private ArrayList<Inquiry> inquiries = new ArrayList<Inquiry>(); //Initialized

    // Constructor
    public ReliefService(Inquirer inquirer) {
        super();
        this.inquirer = inquirer;
    }

    // Getter and setter for inquirer
    public Inquirer getInquirer() {
        return inquirer;
    }

    public void setInquirer(Inquirer inquirer) {
        this.inquirer = inquirer;
    }

    public ArrayList<Inquiry> getInquiries() {
        return this.getInquiries();
    }

    public void addInquiry(DisasterVictim missingPerson, String dateOfInquiry, String infoProvided){
        //Following the rules of composition
        //This is why there is no setInquiries, as we could not implement the rules of composition while setting
        //multiple inquiries at once without it getting out of hand
        this.inquiries.add(new Inquiry(missingPerson, dateOfInquiry, infoProvided));
    }

    public String getLogDetails() {
       StringBuffer log = new StringBuffer();
       log.append("Inquirer: " + this.getInquirer() + "\n");
       for(Inquiry inquiry : this.inquiries){
            log.append(inquiry.getLogDetails());
            log.append("\n");
       }
       return log.toString();
    }

    public void printLogDetails(){
        System.out.println(getLogDetails());
    }

    //Add Inquirer to DB
    public void addInquirerToDB(){
        try{
            //Not completely working, but the logic is there just need to figure out the pkey id conflict
            String query = "INSERT INTO inquirer (firstname, lastname, phonenumber) VALUES (?, ?, ?)";
            PreparedStatement addedInquirer = dbConnect.prepareStatement(query);
            addedInquirer.setString(1, this.getInquirer().getFirstName());
            addedInquirer.setString(2, this.getInquirer().getLastName());
            addedInquirer.setString(3, this.getInquirer().getServicesPhoneNum());
            addedInquirer.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
    }

    //Used in addInquiry()
    private void addInquiryToDB(){

    }

    public static void main(String[] args) {
        Inquirer inquirer = new Inquirer("John", "Alex", "123-456-7890", "Looking for family member");
        ReliefService reliefService = new ReliefService(inquirer);
        reliefService.addInquirerToDB();
        reliefService.close();
    }
}
