package edu.ucalgary.oop;

import java.util.ArrayList;

public interface InquiryLogging{
    public Inquiry logInquiry(Inquiry inquiry);
    public ArrayList<DisasterVictim> searchVictim(String searchTerm);
}
