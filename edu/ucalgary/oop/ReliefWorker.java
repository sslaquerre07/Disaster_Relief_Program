package edu.ucalgary.oop;

import java.awt.*;
import java.util.Scanner;

public class ReliefWorker{
    private Integer workerID;
    protected Scanner scanner;
    private static Integer count = 0;
    protected DisasterVictimLogging victimInterface;
    protected InquiryGUI inquiryInterface;

    public ReliefWorker(){
        this.workerID = count;
        this.scanner = new Scanner(System.in);
        count++;
    } 
}
