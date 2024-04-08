package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class ReliefWorkerTest {
    private ReliefWorker reliefWorker;

    @Before
    public void setUp(){
        reliefWorker = new ReliefWorker();
    }

    @Test
    public void assertObjectCreation(){
        assertNotNull("Object creation should be done successfully", reliefWorker);
    }
    
}

/*Note: Central and Location Workers have parts that mostly depend on user input, so I won't include tests for 
 * them in this project. Similar to how we can't test for the DB as it depends on the data in the database.
 */
