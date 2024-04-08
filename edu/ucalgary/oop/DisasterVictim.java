package edu.ucalgary.oop;

import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;


public class DisasterVictim implements FileAccess{
    private static int counter = 0;

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Integer age;
    private final int ASSIGNED_SOCIAL_ID;
    private ArrayList<FamilyRelation> familyConnections = new ArrayList<>();
    private ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
    private ArrayList<DietaryRestriction> dietaryRestrictions = new ArrayList<>();
    private ArrayList<Supply> personalBelongings = new ArrayList<>();
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

    public static boolean isValidDateFormat(String date) {
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
    public void setFamilyConnections(ArrayList<FamilyRelation> connections) {
        this.familyConnections = connections;
    }

    public void setMedicalRecords(ArrayList<MedicalRecord> records) {
        this.medicalRecords = records;
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

    public void removeFamilyConnection(DisasterVictim relativeToBeRemoved) {
        for(int i = 0; i < this.getFamilyConnections().size(); i++){
            FamilyRelation relation = this.getFamilyConnections().get(0);
            if(relation.getPersonOne() != this && relation.getPersonOne() == relativeToBeRemoved){
                this.getFamilyConnections().remove(relation);
                relativeToBeRemoved.getFamilyConnections().remove(relation);
            }
            else if(relation.getPersonTwo() != this && relation.getPersonTwo() == relativeToBeRemoved){
                this.getFamilyConnections().remove(relation);
                relativeToBeRemoved.getFamilyConnections().remove(relation);
            }
        }
    }

    public void addFamilyConnection(DisasterVictim relative, String relationshipTo) {
        //Creates a new relationship object with the information provided
        ArrayList<FamilyRelation> currentRelations = this.getFamilyConnections();
        FamilyRelation newConnection = new FamilyRelation(this, relationshipTo, relative);

        //Checks if the family relation is valid(SamPeace vs PeaceSam)
        if(!this.checkValidFamilyConnection(relative, this)){
            throw new IllegalArgumentException("Invalid Relation");
        }

        //Adds relationship to both participating members
        this.familyConnections.add(newConnection);
        relative.getFamilyConnections().add(newConnection);

        //Relationship cascades to other family members
        this.cascadeConnections(currentRelations);

    }

    private boolean checkValidFamilyConnection(DisasterVictim newRelative, DisasterVictim originalRelative){
        ArrayList<DisasterVictim> relatives = new ArrayList<>();
        //Populates a list of all relatives
        for(FamilyRelation connection : originalRelative.getFamilyConnections()){
            if(connection.getPersonOne() == originalRelative){
                relatives.add(connection.getPersonTwo());
            }
            else{
                relatives.add(connection.getPersonOne());
            }
        }
        //Compares to see if relationship already exists or not
        for (DisasterVictim relative: relatives){
            if(relative == newRelative){
                return false;
            }
        }

        //Sees if the relationship consists of the same two people
        if(newRelative == originalRelative){
            return false;
        }

        //If it passed the rest, then it is a valid connection!
        return true;
    }

    private void cascadeConnections(ArrayList<FamilyRelation> currentRelations){
        //Iterate and compare all relations to each other
        for(int i = 0; i < currentRelations.size(); i++){
            for(int j = i+1; j<currentRelations.size(); j++){
                FamilyRelation connectionsOuter = currentRelations.get(i);
                FamilyRelation connectionsInner = currentRelations.get(j);

                //See if there are two relations with the same relationshipTo
                if(connectionsInner.getRelationshipTo().equals(connectionsOuter.getRelationshipTo()) && connectionsInner != connectionsOuter){
                    DisasterVictim victim1;
                    DisasterVictim victim2;
                    if(connectionsInner.getPersonOne() != this){
                        victim1 = connectionsInner.getPersonOne();
                    }
                    else{
                        victim1 = connectionsInner.getPersonTwo();
                    }
                    if(connectionsOuter.getPersonOne() != this){
                        victim2 = connectionsOuter.getPersonOne();
                    }
                    else{
                        victim2 = connectionsOuter.getPersonTwo();
                    }

                    //Check if a relationship between the two exists or not
                    if(checkValidFamilyConnection(victim2, victim1)){
                        //Add the new relation
                        FamilyRelation newRelation = new FamilyRelation(victim1, connectionsInner.getRelationshipTo(), victim2);
                        System.out.println(victim1 == victim2);
                        victim1.getFamilyConnections().add(newRelation);
                        victim2.getFamilyConnections().add(newRelation);
                    }
                }
            }
        }
    }


    // Add a MedicalRecord to medicalRecords
    public void addMedicalRecord(MedicalRecord record) {
        medicalRecords.add(record);
    }

    public void addDietaryRestriction(DietaryRestriction restriction){
        if(this.dietaryRestrictions.contains(restriction)){
            throw new IllegalArgumentException("Restriction already listed");
        }
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
        if (!validGender(gender)) {
            throw new IllegalArgumentException("Invalid gender.");
        }
        this.gender = gender.toLowerCase(); // Store in a consistent format
    }

    public boolean validGender(String gender){
        ArrayList<String> validGenders = new ArrayList<>();
        Path filePath = Paths.get("edu\\ucalgary\\oop\\GenderOptions.txt");
        validGenders = readFileLines(filePath); 
        for(String gender1: validGenders){
            if(gender1.equals(gender)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> readFileLines(Path URL){
        ArrayList<String> fileContents = new ArrayList<>();
        BufferedReader reader;
        try{
            reader = new BufferedReader(Files.newBufferedReader(URL));
            String line = reader.readLine();
            while(line != null){
                fileContents.add(line);
                line.trim();
                line = reader.readLine(); 
            }
            reader.close();
        }
        catch(FileNotFoundException ex){
            return null;
        }
        catch(NoSuchFileException ex){
            return null;
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return fileContents;
    }

   
}





