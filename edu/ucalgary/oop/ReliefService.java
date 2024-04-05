package edu.ucalgary.oop;

import java.util.*;

public class ReliefService extends DBAccess{
    private Inquirer inquirer;
    private ArrayList<Inquiry> inquiries = new ArrayList<Inquiry>(); //Initialized
    private ArrayList<ReliefWorker> workers;

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

    public void addReliefWorker(ReliefWorker newWorker){
        this.workers.add(newWorker);
    }

    public void setReliefWorkers(ArrayList<ReliefWorker> workerList){
        this.workers = workerList;
    }

    public ArrayList<ReliefWorker> getReliefWorkers(){
        return this.workers;
    }
}
