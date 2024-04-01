package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InquiryGUI extends JFrame implements ActionListener{
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private DBAccess dbConnection;

    //Components of the login page
    private JLabel loginTitle;
    private JLabel workerTypeLabel;
    private JPanel workerTypeInput;
    private JRadioButtonMenuItem centralWorker;
    private JRadioButtonMenuItem locationWorker;
    private JButton loginButton;

    public InquiryGUI(){
        super("Inquiry Logging GUI");
        //Establish connection to database to search for victims
        dbConnection = new DBAccess();

        setupGUI();
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void setupGUI(){
        //Creates the layout to switch between pages
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //Add all pages to the card panel:
        cardPanel.add(this.setupLogin(), "main");

        //Add cardPanel to the main panel
        getContentPane().add(cardPanel);
    }

    private JPanel setupLogin(){
        //Define all components
        loginTitle = new JLabel("Welcome to the Inquiry Log Login");
        workerTypeLabel = new JLabel("Please select what kind of worker you are");
        workerTypeInput = new JPanel(new GridLayout(1,2));
        ButtonGroup group = new ButtonGroup();
        centralWorker = new JRadioButtonMenuItem("Central Worker");
        locationWorker = new JRadioButtonMenuItem("Location Worker");
        group.add(centralWorker);
        group.add(locationWorker);
        workerTypeInput.add(centralWorker);
        workerTypeInput.add(locationWorker);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        centralWorker.addActionListener(this);
        locationWorker.addActionListener(this);

        //Define all panels
        JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        JPanel headerPanel = new JPanel(new FlowLayout());
        JPanel locationPanel = new JPanel(new GridLayout(1, 2));
        JPanel buttonPanel = new JPanel (new FlowLayout());

        //Add all components to their respective panels
        headerPanel.add(loginTitle);
        locationPanel.add(workerTypeLabel);
        locationPanel.add(workerTypeInput);
        buttonPanel.add(loginButton);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(locationPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == loginButton){
            if(centralWorker.isSelected()){
                //Just displaying a message for now, switch over to inquiry page when done
                JOptionPane.showMessageDialog(this, "You have chosen central worker");
            }
            else if (locationWorker.isSelected()){
                //Just displaying a message for now, switch over to inquiry page when done
                JOptionPane.showMessageDialog(this, "You have chosen location worker");
            }
            else{
                JOptionPane.showMessageDialog(this, "Please choose one of the two options");
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new InquiryGUI().setVisible(true);
        });
    }
    
}
