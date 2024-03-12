package edu.ucalgary.oop;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Inquiry {
    private DisasterVictim missingPerson;
    private String dateOfInquiry;
    private String infoProvided;
    private Location lastKnownLocation;

    // Constructor
    public Inquiry(DisasterVictim missingPerson, String dateOfInquiry, String infoProvided) {
        this.missingPerson = missingPerson;
        setDateOfInquiry(dateOfInquiry);
        this.infoProvided = infoProvided;
    }

    // Getter and setter for missingPerson
    public DisasterVictim getMissingPerson() {
        return missingPerson;
    }

    public void setMissingPerson(DisasterVictim missingPerson) {
        this.missingPerson = missingPerson;
    }

    // Getter and setter for dateOfInquiry
    public String getDateOfInquiry() {
        return dateOfInquiry;
    }

    public void setDateOfInquiry(String dateOfInquiry) {
        // Check if the dateOfInquiry string matches the expected date format
        if (!isValidDateFormat(dateOfInquiry)) {
            throw new IllegalArgumentException("Invalid date format for date of inquiry. Expected format: YYYY-MM-DD");
        }
        this.dateOfInquiry = dateOfInquiry;
    }

    // Getter and setter for infoProvided
    public String getInfoProvided() {
        return infoProvided;
    }

    public void setInfoProvided(String infoProvided) {
        this.infoProvided = infoProvided;
    }

    // Getter and setter for lastKnownLocation
    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    // Helper method to check if a string matches the YYYY-MM-DD date format
    private boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getLogDetails() {
        return ", Missing Person: " + missingPerson.getFirstName() + 
            ", Date of Inquiry: " + dateOfInquiry + 
            ", Info Provided: " + infoProvided + 
            ", Last Known Location: " + lastKnownLocation.getName();
     }
}
