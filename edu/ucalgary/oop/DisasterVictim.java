package edu.ucalgary.oop;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.time.LocalDate;


public class DisasterVictim {
    private static int counter = 0;

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Integer age;
    private final int ASSIGNED_SOCIAL_ID;
    private ArrayList<FamilyRelation> familyConnections = new ArrayList<>();
    private ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
    private ArrayList<DietaryRestriction> dietaryRestrictions = new ArrayList<>();
    private ArrayList<Supply> personalBelongings;
    private final String ENTRY_DATE;
    private String gender;
    private String comments;

    public DisasterVictim(String firstName, String ENTRY_DATE, Integer age) {
        this.firstName = firstName;
        if (!isValidDateFormat(ENTRY_DATE)) {
            throw new IllegalArgumentException("Invalid date format for entry date. Expected format: YYYY-MM-DD");
        }
        this.ENTRY_DATE = ENTRY_DATE;
        this.age = age;
        this.dateOfBirth = null;
        this.ASSIGNED_SOCIAL_ID = generateSocialID();
        
    }

    //Second constructor with Date of birth instead of null
    public DisasterVictim(String firstName, String ENTRY_DATE, String dateOfBirth) {
        this.firstName = firstName;
        if (!isValidDateFormat(ENTRY_DATE) || !isValidDateFormat(dateOfBirth)) {
            throw new IllegalArgumentException("Invalid date format for entry date. Expected format: YYYY-MM-DD");
        }
        this.ENTRY_DATE = ENTRY_DATE;
        this.age = null;
        this.dateOfBirth = dateOfBirth;
        this.ASSIGNED_SOCIAL_ID = generateSocialID();
        
    }

    private static int generateSocialID() {
        counter++;
        return counter;
    }

    private static boolean isValidDateFormat(String date) {
        String dateFormatPattern = "^\\d{4}-\\d{2}-\\d{2}$";
        return date.matches(dateFormatPattern);
    }

  
    // Getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        if (!isValidDateFormat(dateOfBirth)) {
            throw new IllegalArgumentException("Invalid date format for date of birth. Expected format: YYYY-MM-DD");
        }
        this.dateOfBirth = dateOfBirth;
        this.age = null;
    }

    public int getAssignedSocialID() {
        return ASSIGNED_SOCIAL_ID;
    }

    public Integer getAge(){
        return this.age;
    }

    public void setAge(Integer age){
        this.age = age;
        this.dateOfBirth = null;
    }

    public ArrayList<FamilyRelation> getFamilyConnections() {
        return this.familyConnections;
    }

    public ArrayList<DietaryRestriction> getDietaryRestrictions(){
        return this.dietaryRestrictions;
    }

    public ArrayList<MedicalRecord> getMedicalRecords() {
        return this.medicalRecords;
    }   

    public ArrayList<Supply> getPersonalBelongings() {
        return this.personalBelongings;
    }

    // The add and remove methods remain correct.
    
    // Correct the setters to accept Lists instead of arrays
    public void setFamilyConnections(FamilyRelation[] connections) {
        this.familyConnections.clear();
        for (FamilyRelation newRecord : connections) {
            addFamilyConnection(newRecord);
        }
    }

    public void setMedicalRecords(MedicalRecord[] records) {
        this.medicalRecords.clear();
        for (MedicalRecord newRecord : records) {
            addMedicalRecord(newRecord);
        }
    }

    public void setDietaryRestrictions(ArrayList<DietaryRestriction> dietaryRestrictions){
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public void setPersonalBelongings(ArrayList<Supply> belongings) {
        this.personalBelongings = belongings;
    }

    // Add a Supply to personalBelonging
    public void addPersonalBelonging(Supply supply) {
        this.personalBelongings.add(supply);
    }

    // Remove a Supply from personalBelongings, we assume it only appears once
    public void removePersonalBelonging(Supply unwantedSupply) {
        ArrayList<Supply> updatedBelongings = new ArrayList<>();
        for (Supply supply : this.personalBelongings) {
            if (!supply.equals(unwantedSupply)) {
                updatedBelongings.add(supply);
            }
        }
        this.setPersonalBelongings(updatedBelongings);
    }

    public void removeFamilyConnection(FamilyRelation exRelation) {
        familyConnections.remove(exRelation);
    }

    public void addFamilyConnection(FamilyRelation record) {
        familyConnections.add(record);
    }


    // Add a MedicalRecord to medicalRecords
    public void addMedicalRecord(MedicalRecord record) {
        medicalRecords.add(record);
    }

    public void addDietaryRestriction(DietaryRestriction restriction){
        this.dietaryRestrictions.add(restriction);
    }

    public String getEntryDate() {
        return ENTRY_DATE;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments =  comments;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (!gender.matches("(?i)^(male|female|other)$")) {
            throw new IllegalArgumentException("Invalid gender. Acceptable values are male, female, or other.");
        }
        this.gender = gender.toLowerCase(); // Store in a consistent format
    }

   
}


//Dietary Restrictions Declaration
enum DietaryRestriction {
    AVML,
    DBML,
    GFML,
    KSML,
    LSML,
    MOML,
    PFML,
    VGML,
    VJML
}





