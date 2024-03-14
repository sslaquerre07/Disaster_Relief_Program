/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024
Licensed under GPL v3
See LICENSE.txt for more information.
*/
package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class ReliefServiceTest{
    private ReliefService reliefService;
    private Inquirer inquirer;

    @Before
    public void setUp() {
        // Assuming Inquirer, DisasterVictim, and Location have constructors as implied
        inquirer = new Inquirer("John", "Alex", "1234567890", "Looking for family member");
        reliefService = new ReliefService(inquirer);
    }

    @Test
    public void testObjectCreation() {
        assertNotNull("ReliefService object should not be null", reliefService);
    }

    @Test
    public void testSetAndGetInquirer() {
        Inquirer inquirer1 = new Inquirer("Sam", "Laquerre", "3028390485", "Looking for family member");
        reliefService.setInquirer(inquirer1);
        assertEquals("Inquirer should match the one set in setup", inquirer1, reliefService.getInquirer());
    }

    @Test 
    public void testGetInquiries(){
        DisasterVictim victim = new DisasterVictim("Brad", "2022-07-04", 12);
        String dateOfInquiry = "2023-08-09";
        String infoProvided = "Looking for family members";
        reliefService.addInquiry(victim, dateOfInquiry, infoProvided);
        ArrayList<Inquiry> expectedValue = new ArrayList<Inquiry>();
        expectedValue.add(new Inquiry(victim, dateOfInquiry, infoProvided));
        boolean correct = false;

        ArrayList<Inquiry> actualValue = reliefService.getInquiries();
        if(actualValue.get(0).getMissingPerson() == expectedValue.get(0).getMissingPerson()){
            if(actualValue.get(0).getDateOfInquiry() == expectedValue.get(0).getDateOfInquiry()){
                if(actualValue.get(0).getInfoProvided() == expectedValue.get(0).getInfoProvided()){
                    correct = true;
                }
            }
        }

        assertTrue("Should retrieve the inquiry that was added", correct);
    }

    @Test
    public void testGetLogDetails() {
        //Assume the default inquirer is used
        DisasterVictim victim = new DisasterVictim("Brad", "2022-07-04", 12);
        String dateOfInquiry = "2023-08-09";
        String infoProvided = "Looking for family members";
        reliefService.addInquiry(victim, dateOfInquiry, infoProvided);

        String actualString = reliefService.getLogDetails();
        String expectedString = "Inquirer: " + reliefService.getInquirer() + "\n"
                                + reliefService.getInquiries().get(0).getLogDetails() + "\n";
        
        assertTrue("Should get the log details correctly", actualString.equals(expectedString));

    }

    @Test
    public void testSearchVictim(){
        DisasterVictim victim1 = new DisasterVictim("Praveen", "2022-09-09", 11);
        DisasterVictim victim2 = new DisasterVictim("Oprah", "2022-09-09", 11);
        //Not implemented yet, but in theory add victims to the database to be able to retrieve them

        boolean correct = false;
        //Searches and returns an array of the results
        ArrayList<DisasterVictim> results = reliefService.searchVictim("Pra");
        if(results.contains(victim1) && results.contains(victim2)){
            correct = true;
        }

        assertTrue("The search results should both contain Oprah and Praveen", correct);
    }

    @Test
    public void testInquiryLogging(){
        DisasterVictim victim1 = new DisasterVictim("Praveen", "2022-09-09", 11);
        Inquiry newInquiry = new Inquiry(victim1, "2022-08-01", "Basic Info");
        //In future implementation, the method will go through some try and catch blocks and if it works successfully,
        //it will return the original inquiry that was passed in.
        assertEquals("Logging this should return the original inquiry", newInquiry, reliefService.logInquiry(newInquiry));
    }
}
