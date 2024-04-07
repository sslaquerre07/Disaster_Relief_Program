package edu.ucalgary.oop;

import java.awt.*;
import java.util.Scanner;

public class CentralWorker extends ReliefWorker{
    public CentralWorker(){
        super();
        this.scanner = new Scanner(System.in);
        this.inquiryInterface = new InquiryGUI();
        this.victimInterface = new DisasterVictimLogging();
    }

    public void enterInquiry(){
        EventQueue.invokeLater(() -> {
            this.inquiryInterface.setVisible(true);
        });
    }

    public void enterVictim(){
        //Align all of this input with some database access and this interface should work great!
        EventQueue.invokeLater(() -> {
            this.victimInterface.setVisible(true);
        }); 
    }

    public void mainProgram(){
        System.out.println("Please choose from the following options(Enter the number it corresponds to)");
        System.out.println("1.Enter new Victim\t2.Enter new Inquiry");
        String input = this.scanner.nextLine();
        if(input.equals("1")){
            this.enterVictim();
        }
        else if (input.equals("2")){
            this.enterInquiry();
        }
        else{
            System.out.println("Invalid input, please try again");
        }
    }

    public static void main(String[] args) {
        CentralWorker newWorker = new CentralWorker();
        newWorker.mainProgram();
    }
    
}
