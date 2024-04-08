
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
    public void testSetAndGetInquiry(){
        Inquiry testInquiry = new Inquiry(new DisasterVictim("Test", "2022-02-02", 15), "2022-05-05", "default info");
        ArrayList<Inquiry> test = new ArrayList<>();
        test.add(testInquiry);
        reliefService.setInquiries(test);
        assertEquals("These two should be equal", test, reliefService.getInquiries());
    }

    @Test
    public void testAddInquiry(){
        boolean validInfo = true;
        DisasterVictim missingPerson = new DisasterVictim("Test", "2022-02-02", 15);
        Inquiry testInquiry = new Inquiry(missingPerson, "2022-05-05", "default info");
        reliefService.addInquiry(missingPerson, "2022-05-05", "default info");
        if (reliefService.getInquiries().get(0).getMissingPerson() != missingPerson){
            validInfo = false;
        }
        if(!reliefService.getInquiries().get(0).getDateOfInquiry().equals("2022-05-05")){
            validInfo = false;
        }
        if(!reliefService.getInquiries().get(0).getInfoProvided().equals("default info")){
            validInfo = false;
        }
        assertTrue("Add Inquiry should make all of the test values equal", validInfo);
    }

    @Test
    public void testSetAndGetReliefWorkers(){
        ReliefWorker worker = new ReliefWorker();
        ArrayList<ReliefWorker> workerList = new ArrayList<>();
        workerList.add(worker);
        reliefService.setReliefWorkers(workerList);
        assertEquals("The two arrays should be the same", workerList, reliefService.getReliefWorkers());
    }

    @Test
    public void testAddReliefWorkers(){
        ReliefWorker worker = new ReliefWorker();
        ArrayList<ReliefWorker> workerList = new ArrayList<>();
        workerList.add(worker);
        reliefService.addReliefWorker(worker);
        assertEquals("The two arrays should be the same", workerList, reliefService.getReliefWorkers());
    }
}
