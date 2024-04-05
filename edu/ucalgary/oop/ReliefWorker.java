package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class ReliefWorker{
    private Integer workerID;
    private Scanner scanner;
    private static Integer count = 0;
    private DisasterVictimLogging victimInterface;
    private InquiryGUI inquiryInterface;

    public ReliefWorker(){
        this.workerID = count;
        this.scanner = new Scanner(System.in);
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

    public void mainProgram(){
        System.out.println("Please choose from the following options(Enter the number it corresponds to)");
        while(true){
            System.out.println("1.Enter new Victim\t2.Enter new Inquiry\t3.Quit");
            String input = this.scanner.nextLine();
            if(input.equals("1")){
                this.enterVictim();
            }
            else if (input.equals("2")){
                this.enterInquiry();
            }
            else if(input.equals("3")){
                break;
            }
            else{
                System.out.println("Invalid input, please try again");
            }
        }
    }

    public static void main(String[] args) {
        ReliefWorker newWorker = new ReliefWorker();
        newWorker.mainProgram();
    }
}
