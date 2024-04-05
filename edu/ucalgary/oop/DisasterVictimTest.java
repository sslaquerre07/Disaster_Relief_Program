
package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class DisasterVictimTest {
    private DisasterVictim victim;
    private List<Supply> suppliesToSet; 
    private List<FamilyRelation> familyRelations; 
    private Integer expectedAge = 12;
    private String expectedDateOfBirth = "2012-06-09";
    private String expectedFirstName = "Freda";
    private String EXPECTED_ENTRY_DATE = "2024-01-18";
    private String validDate = "2024-01-15";
    private String invalidDate = "15/13/2024";
    private String expectedGender = "female"; 
    private String expectedComments = "Needs medical attention and speaks 2 languages";

    @Before
    public void setUp() {
        //By default use the constructor with age to make things a little simpler
        victim = new DisasterVictim(expectedFirstName, EXPECTED_ENTRY_DATE, expectedAge);
        suppliesToSet = new ArrayList<>();
        suppliesToSet.add(new Supply("Water Bottle", 10));
        suppliesToSet.add(new Supply("Blanket", 5));
        
        DisasterVictim victim1 = new DisasterVictim("Jane", "2024-01-20", 12);
        DisasterVictim victim2 = new DisasterVictim("John", "2024-01-22", "2012-06-09");
        
    }

  		  
    /*Constructor Related Tests */
  @Test
    public void testConstructorWithValidEntryDate() {
        String validEntryDate = "2024-01-18";
        DisasterVictim victim = new DisasterVictim("Freda", validEntryDate, 13);
        assertNotNull("Constructor should successfully create an instance with a valid entry date", victim);
        assertEquals("Constructor should set the entry date correctly", validEntryDate, victim.getEntryDate());
    }
  @Test
    public void testConstructorWithValidBirthDate() {
        String validBirthDate = "2012-06-09";
        DisasterVictim victim2 = new DisasterVictim("John", "2024-01-22", validBirthDate);
        assertNotNull("Constructor should successfully create an instance with a valid birth date", victim2);
        assertEquals("Constructor should set the entry date correctly", validBirthDate, victim2.getDateOfBirth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidEntryDateFormat() {
        String invalidEntryDate = "18/01/2024"; // Incorrect format according to your specifications
        new DisasterVictim("Freda", invalidEntryDate, 12);
        // Expecting IllegalArgumentException due to invalid date format
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidBirthDateFormat() {
        String invalidBirthDate = "18/01/2024"; // Incorrect format according to your specifications
        new DisasterVictim("Freda", "2024-01-18", invalidBirthDate);
        // Expecting IllegalArgumentException due to invalid date format
    }
    /*End of Constructor Related Tests */


    /*DOB/Age Related Tests */
   @Test
    public void testSetDateOfBirth() {
        DisasterVictim victim = new DisasterVictim("Test", "2024-01-02", "2004-08-11");
        String newDateOfBirth = "1987-05-21";
        victim.setDateOfBirth(newDateOfBirth);
        assertEquals("setDateOfBirth should correctly update the date of birth", newDateOfBirth, victim.getDateOfBirth());
        assertNull("When date of birth is set, the age should always be null", victim.getAge());
    }

    @Test
    public void testSetAgeWithDateOfBirthSet(){
        //Should make the age null
        String newDateOfBirth = "1987-05-21";
        victim.setDateOfBirth(newDateOfBirth);
        Integer newAge = 15;
        victim.setAge(newAge);
        assertEquals("setAge should correctly update the age", newAge, victim.getAge());
        assertNull("When date of birth is set, the age should always be null", victim.getDateOfBirth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfBirthWithInvalidDateFormat(){
        String invalidBirthDate = "18/01/2024"; // Incorrect format according to your specifications
        victim.setDateOfBirth(invalidBirthDate);
        // Expecting IllegalArgumentException due to invalid date format
    }

    @Test
    public void testSetAge() {
        Integer newAge = 15;
        victim.setAge(newAge);
        assertEquals("setAge should correctly update the date of birth", newAge, victim.getAge());
        assertNull("When age is set, the date of birth should always be null", victim.getDateOfBirth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfBirthWithInvalidFormat() {
        victim.setDateOfBirth(invalidDate); // This format should cause an exception
    }
    /*End of DOB/Age Related Tests */

	
    /*Name Related Tests */
	@Test
    public void testSetAndGetFirstName() {
        String newFirstName = "Alice";
        victim.setFirstName(newFirstName);
        assertEquals("setFirstName should update and getFirstName should return the new first name", newFirstName, victim.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        String newLastName = "Smith";
        victim.setLastName(newLastName);
        assertEquals("setLastName should update and getLastName should return the new last name", newLastName, victim.getLastName());
    }
    /*End of Name Related Tests */


    /*Comments Related Tests */
    @Test
    public void testGetComments() {
        victim.setComments(expectedComments);
        assertEquals("getComments should return the initial correct comments", expectedComments, victim.getComments());
    }

    @Test
    public void testSetComments() {
        victim.setComments(expectedComments);
        String newComments = "Has a minor injury on the left arm";
        victim.setComments(newComments);
        assertEquals("setComments should update the comments correctly", newComments, victim.getComments());
    }
    /* End of Comments Related Tests */


    /*SocialID Related Tests */
    @Test
    public void testGetAssignedSocialID() {
        // The next victim should have an ID one higher than the previous victim
        // Tests can be run in any order so two victims will be created
        DisasterVictim newVictim = new DisasterVictim("Kash", "2024-01-21", 13);
        int expectedSocialId = newVictim.getAssignedSocialID() + 1;
        DisasterVictim actualVictim = new DisasterVictim("Adeleke", "2024-01-22", 13);

        assertEquals("getAssignedSocialID should return the expected social ID", expectedSocialId, actualVictim.getAssignedSocialID());
    }
    /*End of SocialID Related Tests */


    /*EntryDate Related Tests */
    @Test
    public void testGetEntryDate() {
        assertEquals("getEntryDate should return the expected entry date", EXPECTED_ENTRY_DATE, victim.getEntryDate());
    }
    /*End of EntryDate Related Tests */

   
    /*Gender Related Tests */
    @Test
    public void testSetAndGetGender() {
        String newGender = "male";
        victim.setGender(newGender);
        assertEquals("setGender should update and getGender should return the new gender", newGender.toLowerCase(), victim.getGender());
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testSetGenderWithInvalidGender(){
        String newGender = "wman";
        victim.setGender(newGender);
    }

    @Test
    public void testInvalidFileName(){
        //Since the function returns null when a fileNotFoundException is caught, we'll test with an assert statement
        String invalidFile = "randomNonsenseLol";
        assertNull(victim.readFileLines(invalidFile));
    }
    /*End of Gender Related Tests */

    /*Dietary Restriction Related Tests */
    @Test 
    public void testSetandGetDietaryRestrictions(){
        DisasterVictim victim1 = new DisasterVictim("Jane", EXPECTED_ENTRY_DATE, expectedDateOfBirth);
        ArrayList<DietaryRestriction> dietaryRestrictions = new ArrayList<>();
        dietaryRestrictions.add(DietaryRestriction.AVML);
        dietaryRestrictions.add(DietaryRestriction.GFML);

        victim1.setDietaryRestrictions(dietaryRestrictions);
        assertEquals("Getter should get the array retrieved by the setter", dietaryRestrictions, victim1.getDietaryRestrictions());
    }

    @Test 
    public void testAddDietaryRestrictions(){
        DisasterVictim victim1 = new DisasterVictim("Jane", EXPECTED_ENTRY_DATE, expectedDateOfBirth);
        victim1.addDietaryRestriction(DietaryRestriction.GFML);

        //Should add the allergy to the empty list, making the size of the array 1.
        assertEquals("The add DietaryRestriction should add the restriction", victim1.getDietaryRestrictions().size(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDietaryRestrictionsDuplicate(){
        DisasterVictim victim1 = new DisasterVictim("Jane", EXPECTED_ENTRY_DATE, expectedDateOfBirth);
        victim1.addDietaryRestriction(DietaryRestriction.GFML);
        //This should throw an IllegalArgumentException since the restriction is already in the array
        victim1.addDietaryRestriction(DietaryRestriction.GFML);
    }
    /*End of Dietary Restriction Related Tests */

    /*Family Connection Related Tests */
    @Test
    public void testAddFamilyConnection() {
        //Tests standard family connection
        DisasterVictim victim1 = new DisasterVictim("Jane", "2024-01-20", 12);
        DisasterVictim victim2 = new DisasterVictim("John", "2024-01-22", 30);

        FamilyRelation relation = new FamilyRelation(victim2, "parent", victim1);
        ArrayList<FamilyRelation> expectedRelations = new ArrayList<>();
        expectedRelations.add(relation);
        victim2.setFamilyConnections(expectedRelations);

        ArrayList<FamilyRelation> testFamily = victim2.getFamilyConnections();
        boolean correct = false;

        if ((testFamily!=null) && (testFamily.get(0) == expectedRelations.get(0))) {
                correct = true;
        }
        assertTrue("addFamilyConnection should add a family relationship", correct);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFamilyConnectionDuplicate(){
        //RelationshipJaneJohn and RelationshipJohnJane are arbitrary
        DisasterVictim victim1 = new DisasterVictim("Jane", "2024-01-20", 12);
        DisasterVictim victim2 = new DisasterVictim("John", "2024-01-22", 15);

        //This line should be fine
        victim1.addFamilyConnection(victim2, "sibling");
        //Relation should already be added, therefore causing an error for duplicate data.
        victim2.addFamilyConnection(victim1, "sibling");
    }

    @Test
    public void testAddFamilyConnectionsBothVictims(){
        //Tests that both DisasterVictims receive a family connection once one is set
        DisasterVictim victim1 = new DisasterVictim("Jane", "2024-01-20", 12);
        DisasterVictim victim2 = new DisasterVictim("John", "2024-01-22", 30);

        victim2.addFamilyConnection(victim1, "parent");

        ArrayList<FamilyRelation> testFamilyParentSide = victim2.getFamilyConnections();
        ArrayList<FamilyRelation> testFamilyChildSide = victim1.getFamilyConnections();
        boolean correct = false;

        if ((testFamilyParentSide.get(0) == testFamilyChildSide.get(0))) {
                correct = true;
        }
        assertTrue("addFamilyConnection should add a family relationship to both members", correct);
    }

    @Test 
    public void testAddFamilyConnectionsThreePeople(){
        //Tests that both DisasterVictims receive a family connection once one is set
        DisasterVictim victim1 = new DisasterVictim("Jane", "2024-01-20", 12);
        DisasterVictim victim2 = new DisasterVictim("John", "2024-01-22", 13);
        DisasterVictim victim3 = new DisasterVictim("Jack", "2024-01-22", 16);

        victim2.addFamilyConnection(victim1, "sibling");
        victim2.addFamilyConnection(victim3, "sibling");

        ArrayList<FamilyRelation> testFamilySibling1 = victim1.getFamilyConnections();
        ArrayList<FamilyRelation> testFamilySibling2 = victim2.getFamilyConnections();
        ArrayList<FamilyRelation> testFamilySibling3 = victim3.getFamilyConnections();
        boolean correct = false;

        if((testFamilySibling1.size() == 2) && (testFamilySibling2.size() == 2) && (testFamilySibling3.size() == 2)){
            correct = true;
        }
        assertTrue("The second add Family connection should also connect siblings 1 and 3", correct);
    }

    @Test
    public void testRemoveFamilyConnection() {
        DisasterVictim victim = new DisasterVictim("Freda", "2024-01-23", 20);
        DisasterVictim victim1 = new DisasterVictim("Jane", "2024-01-20", 18);
        DisasterVictim victim2 = new DisasterVictim("John", "2024-01-22", 19);
        FamilyRelation relation1 = new FamilyRelation(victim, "sibling", victim1);
        FamilyRelation relation2 = new FamilyRelation(victim, "sibling", victim2);
        ArrayList<FamilyRelation> originalRelations = new ArrayList<>(Arrays.asList(relation1, relation2));
        victim.setFamilyConnections(originalRelations);

        victim.removeFamilyConnection(victim1);

        ArrayList<FamilyRelation> testFamily = victim.getFamilyConnections();
        boolean correct = true;

        int i;
        //Looks for relation1 as it should have been deleted
        for (i = 0; i < testFamily.size(); i++) {
            if (testFamily.get(i) == relation1) {
                correct = false;
            }
        }
        assertTrue("removeFamilyConnection should remove the family member", correct);
    }

    @Test 
    public void testRemoveFamilyConnectionSideEffects(){
        //If person 1 and 2 are related, removing the connection from on person 1 should also remove the connection on person 2
        DisasterVictim victim1 = new DisasterVictim("Jane", "2024-01-20", 25);
        DisasterVictim victim2 = new DisasterVictim("John", "2024-01-22", 24);
        //This next line was tested in a previous test, but this should also add the connection in victim2
        victim1.addFamilyConnection(victim2, "sibling");
        //Since the connection was added in victim2, we should be able to remove it in victim2
        victim2.removeFamilyConnection(victim1);
        //Check if victim1's family relations are empty now that we removed the connection
        assertEquals("Removing the connection should remove the relation from both parties", victim1.getFamilyConnections().size(), 0);
    }

    @Test
    public void testSetFamilyConnection() {
        DisasterVictim victim1 = new DisasterVictim("Jane", "2024-01-20", 33);
        DisasterVictim victim2 = new DisasterVictim("John", "2024-01-22", 44);

        FamilyRelation relation = new FamilyRelation(victim1, "sibling", victim2);
        ArrayList<FamilyRelation> expectedRelations = new ArrayList<>();
        expectedRelations.add(relation);
        victim1.setFamilyConnections(expectedRelations);
        boolean correct = true;

       // We have not studied overriding equals in arrays of custom objects so we will manually evaluate equality
       ArrayList<FamilyRelation> actualRecords = victim1.getFamilyConnections();
       if (expectedRelations.size() != actualRecords.size()) {
           correct = false;
       } else {    
           int i;
           for (i=0;i<actualRecords.size();i++) {
               if (expectedRelations.get(i) != actualRecords.get(i)) {
                   correct = false;
               }
           }
       }
       assertTrue("Family relation should be set", correct);
    }
    /*End of Family Connection Related Tests */  


    /*Supply Related Tests */
    @Test
    public void testAddPersonalBelonging() {
        Supply newSupply = new Supply("Emergency Kit", 1);
        victim.addPersonalBelonging(newSupply);
        ArrayList<Supply> testSupplies = victim.getPersonalBelongings();
        boolean correct = false;
 
        int i;
        for (i = 0; i < testSupplies.size(); i++) {
            if (testSupplies.get(i) == newSupply) {
                correct = true;
            }
        }
        assertTrue("addPersonalBelonging should add the supply to personal belongings", correct);
    }

    @Test
    public void testRemovePersonalBelonging() {
        
            Supply supplyToRemove = suppliesToSet.get(0); 
            victim.addPersonalBelonging(supplyToRemove); 
            victim.removePersonalBelonging(supplyToRemove);

            ArrayList<Supply> testSupplies = victim.getPersonalBelongings();
            boolean correct = true;
    
            int i;
            for (i = 0; i < testSupplies.size(); i++) {
                if (testSupplies.get(i) == supplyToRemove) {
                    correct = false;
                }
            }
        assertTrue("removePersonalBelonging should remove the supply from personal belongings", correct);
    }

    @Test
    public void testSetPersonalBelongings() {
        Supply one = new Supply("Tent", 1);
        Supply two = new Supply("Jug", 3);
        ArrayList<Supply> newSupplies = new ArrayList<>();
        newSupplies.add(one);
        newSupplies.add(two);
        boolean correct = true;

        victim.setPersonalBelongings(newSupplies);
        ArrayList<Supply> actualSupplies = victim.getPersonalBelongings();

        // We have not studied overriding equals in arrays of custom objects so we will manually evaluate equality
        if (newSupplies.size() != actualSupplies.size()) {
            correct = false;
        } else {
            int i;
            for (i=0;i<newSupplies.size();i++) {
                if (actualSupplies.get(i) != newSupplies.get(i)) {
                    correct = false;
                }
            }
        }
        assertTrue("setPersonalBelongings should correctly update personal belongings", correct);
    }
    /*End of Supply Related Tests */


    /*Medical Records Related Tests */
  @Test
    public void testSetMedicalRecords() {
        Location testLocation = new Location("Shelter Z", "1234 Shelter Ave");
        MedicalRecord testRecord = new MedicalRecord(testLocation, "test for strep", "2024-02-09");
        boolean correct = true;

        ArrayList<MedicalRecord> newRecords = new ArrayList<>();
        newRecords.add(testRecord);
        victim.setMedicalRecords(newRecords);
        ArrayList<MedicalRecord> actualRecords = victim.getMedicalRecords();

        // We have not studied overriding equals in arrays of custom objects so we will manually evaluate equality
        if (newRecords.size() != actualRecords.size()) {
            correct = false;
        } else {
            int i;
            for (i=0;i<newRecords.size();i++) {
                if (actualRecords.get(i) != newRecords.get(i)) {
                    correct = false;
                }
            }
        }
        assertTrue("setMedicalRecords should correctly update medical records", correct);
        //Note: Setting a Medical Record with an invalid date is tested in the medical record class, so no need to do it here
    }
    /*End of Medical Records Related Tests */


    
}





