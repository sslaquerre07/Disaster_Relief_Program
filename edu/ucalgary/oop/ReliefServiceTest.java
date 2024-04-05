
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
}
