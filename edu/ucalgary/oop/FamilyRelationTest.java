
package edu.ucalgary.oop;


import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FamilyRelationTest {

    private DisasterVictim personOne = new DisasterVictim("John Dalan", "2024-01-19", 13);
    private DisasterVictim personTwo = new DisasterVictim("Jane Dalan", "2024-02-20", 14);
    private String relationshipTo = "sibling";
    private FamilyRelation testFamilyRelationObject = new FamilyRelation(personOne, relationshipTo, personTwo);
    
    @Test
    public void testObjectCreation() {
        assertNotNull(testFamilyRelationObject);
    }
	
    @Test
    public void testSetAndGetPersonOne() {
        DisasterVictim newPersonOne = new DisasterVictim("New Person", "2024-03-21", 10);
        testFamilyRelationObject.setPersonOne(newPersonOne);
        assertEquals("setPersonOne should update personOne", newPersonOne, testFamilyRelationObject.getPersonOne());
    }

    @Test
    public void testSetAndGetPersonTwo() {
        DisasterVictim newPersonTwo = new DisasterVictim("Another Person", "2024-04-22", 77);
        testFamilyRelationObject.setPersonTwo(newPersonTwo);
        assertEquals("setPersonTwo should update personTwo", newPersonTwo, testFamilyRelationObject.getPersonTwo());
    }

    @Test
    public void testSetAndGetRelationshipTo() {
        String newRelationship = "parent";
        testFamilyRelationObject.setRelationshipTo(newRelationship);
        assertEquals("setRelationshipTo should update the relationship", newRelationship, testFamilyRelationObject.getRelationshipTo());
    }

    //Test all relationship constraints presented in the assignment
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
}
