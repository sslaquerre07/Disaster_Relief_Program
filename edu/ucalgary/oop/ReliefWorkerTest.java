package edu.ucalgary.oop;


import org.junit.*;

import static org.junit.Assert.*;

public class ReliefWorkerTest {
    private ReliefWorker worker = new ReliefWorker();
    private DisasterVictim victim = new DisasterVictim("John", "2022-08-11", 19);
    private Location location = new Location("Telus-Spark", "234 Country-Hills BLVD");

    @Test
    public void testEnterVictim(){
        //Test that the insertion of the Disaster Victim was successful by going through the whole function
        assertEquals("victim and the victim returned should be the same", victim, worker.enterVictim(victim, location));
    }
}
