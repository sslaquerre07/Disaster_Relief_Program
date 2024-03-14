package edu.ucalgary.oop;

import java.util.*;

public class ReliefService extends UserInteraction implements InquiryLogging{
    private Inquirer inquirer;
    private ArrayList<Inquiry> inquiries = new ArrayList<Inquiry>(); //Initialized

    // Constructor
    public ReliefService(Inquirer inquirer) {
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

    @Override
    public Inquiry logInquiry(Inquiry inquiry) {
        // TODO Auto-generated method stub
        //Terminal input methods here
        //Possibly store the input in a database?
        //Add it to the inquiries list for this inquirer
        return inquiry;
    }

    @Override
    public ArrayList<DisasterVictim> searchVictim(String searchTerm) {
        // TODO Auto-generated method stub
        //Terminal input methods here
        //Return all Disaster victims(how??) and either display or return them as needed.
        return new ArrayList<>();
        
    }

    @Override
    public <T> void getTerminalInput() {
        // TODO Auto-generated method stub
        super.getTerminalInput();
    }
}
