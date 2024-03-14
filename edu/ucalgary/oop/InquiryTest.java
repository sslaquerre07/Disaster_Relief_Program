package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class InquiryTest {
    private Inquiry inquiry;
    private DisasterVictim missingPerson;
    private Location lastKnownLocation;
    private String validDate = "2024-02-10";
    private String invalidDate = "2024/02/10";
    private String expectedInfoProvided = "Looking for family member";
    private String expectedLogDetails = "Missing Person: Jane Alex, Date of Inquiry: 2024-02-10, Info Provided: Looking for family member, Last Known Location: University of Calgary";

    @Before
    public void setUp() {
        // Assuming Inquirer, DisasterVictim, and Location have constructors as implied
        missingPerson = new DisasterVictim("Jane Alex", "2024-01-25", 14);
        lastKnownLocation = new Location("University of Calgary", "2500 University Dr NW");
        inquiry = new Inquiry(missingPerson, validDate, expectedInfoProvided);
    }

    @Test
    public void testObjectCreation() {
        assertNotNull("Inquiry object should not be null", inquiry);
    }

    @Test
    public void testGetMissingPerson() {
        assertEquals("Missing person should match the one set in setup", missingPerson, inquiry.getMissingPerson());
    }

    @Test
    public void testGetDateOfInquiry() {
        assertEquals("Date of inquiry should match the one set in setup", validDate, inquiry.getDateOfInquiry());
    }

    @Test
    public void testGetInfoProvided() {
        assertEquals("Info provided should match the one set in setup", expectedInfoProvided, inquiry.getInfoProvided());
    }

    @Test
    public void testGetLastKnownLocation() {
        assertEquals("Last known location should match the one set in setup", lastKnownLocation, inquiry.getLastKnownLocation());
    }

    @Test
    public void testSetDateOfInquiryWithValidDate() {
        inquiry.setDateOfInquiry(validDate);
        assertEquals("Setting a valid date should update the date of inquiry", validDate, inquiry.getDateOfInquiry());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfInquiryWithInvalidDate() {
        inquiry.setDateOfInquiry(invalidDate); // This should throw IllegalArgumentException due to invalid format
    }

    @Test
    public void testGetLogDetails() {
        assertEquals("Log details should match the expected format", expectedLogDetails, inquiry.getLogDetails());
    }
}
