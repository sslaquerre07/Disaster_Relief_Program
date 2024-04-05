package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReliefWorker{
    private Integer workerID;
    private static Integer count = 0;
    private DisasterVictimLogging victimInterface;
    private InquiryGUI inquiryInterface;

    public ReliefWorker(){
        this.workerID = count;
        this.victimInterface = new DisasterVictimLogging();
        this.inquiryInterface = new InquiryGUI();
        count++;
    } 

    public void enterVictim(){
        //Align all of this input with some database access and this interface should work great!
        EventQueue.invokeLater(() -> {
            victimInterface.setVisible(true);
        }); 
    }

    public void enterInquiry(){
        //Align all of this input with some database access and this interface should work great!
        EventQueue.invokeLater(() -> {
            inquiryInterface.setVisible(true);
        }); 
    }

    public static void main(String[] args) {
        ReliefWorker worker= new ReliefWorker();
        worker.enterInquiry();
    }
}
