package edu.ucalgary.oop;

import java.awt.*;
import java.util.Scanner;
import java.sql.*;

public class LocationWorker extends ReliefWorker{
    private DBAccess dbAccess;
    private Location location;
    private Integer locationID;

    public LocationWorker(){
        super();
        this.dbAccess = new DBAccess();
    }

    public void setLocation(Location location){
        this.location = location;
    }

    //Enter
    public void enterInquiry(){
        super.inquiryInterface = new InquiryGUI(this.location);
        EventQueue.invokeLater(() -> {
            super.inquiryInterface.setVisible(true);
        });
    }

    public void enterVictim(){
        super.victimInterface = new DisasterVictimLogging(this.locationID);
        //Align all of this input with some database access and this interface should work great!
        EventQueue.invokeLater(() -> {
            this.victimInterface.setVisible(true);
        }); 
    }

    public Location getLocation(){
        System.out.println("Please select your location from the following list");
        ResultSet locations = this.dbAccess.retrieveAllLocations();
        String locationString = this.dbAccess.stringifyResults(locations);
        //Resetting locations so it can be iterated over once again
        locations = this.dbAccess.retrieveAllLocations();
        int numberLocations = this.dbAccess.getResultSetSize(locations);
        int userChoice;
        while (true) {
            System.out.println(locationString);
            try {
                userChoice = Integer.parseInt(this.scanner.nextLine());
                if (userChoice != 0 && userChoice <= numberLocations) {
                    System.out.println("Location chosen successfully");
                    break;
                } else {
                    System.out.println("Invalid LocationID, please choose again");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        this.locationID = userChoice;
        ResultSet location = this.dbAccess.retrieveLocation(userChoice);
        String locationName = "Null";
        String locationAddress = "Null";
        try{
            if(location.next()){
                locationName = location.getString("name");
                locationAddress = location.getString("address");
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return new Location(locationName, locationAddress);
    }

    public void mainProgram(){
        //Main user program
        System.out.println("Please choose from the following options(Enter the number it corresponds to)");
        System.out.println("1.Enter new Victim\t2.Enter new Inquiry");
        String input = this.scanner.nextLine();
        if(input.equals("1")){
            this.enterVictim();
        }
        else if (input.equals("2")){
            this.enterInquiry();
        }
        else{
            System.out.println("Invalid input, please try again");
        }
    }

    public static void main(String[] args) {
        LocationWorker worker = new LocationWorker();
        worker.setLocation(worker.getLocation());
        worker.mainProgram();
    }
}
