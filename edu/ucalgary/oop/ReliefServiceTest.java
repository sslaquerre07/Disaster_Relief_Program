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
        reliefService.addInquiries(victim, dateOfInquiry, infoProvided);
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

    //Come back to this one with more time!!
    @Test
    public void testGetLogDetails() {
        //TODO ON THURSDAY
    }
}
