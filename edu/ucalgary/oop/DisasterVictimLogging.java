package edu.ucalgary.oop;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class DisasterVictimLogging extends JFrame implements ActionListener, MouseListener{
    private CardLayout cardLayout;
    private JPanel cardPanel;
    //All the buttons and labels on the main page
    private JLabel title;
    private JButton relationshipsButton;
    private JButton medicalRecordsButton;

    //Labels for the main page
    private JLabel fnLabel;
    private JLabel lnLabel;
    private JLabel ageLabel;
    private JLabel dobLabel;
    private JLabel genderLabel;
    private JLabel commentsLabel;

    //Input titles
    private JTextField fnInput;
    private JTextField lnInput;
    private JSpinner ageSpinner;
    private JTextField dobInput;
    private JTextField genderInput;
    private JTextField commentsInput;

    public DisasterVictimLogging(){
        super("Disaster Victim Logging GUI");

        setupGUI();
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void setupGUI(){
        //Creates the layout to switch between pages
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        title = new JLabel("Welcome to the Disaster Victim Logging Page");

        //Set all labels
        fnLabel = new JLabel("First Name: ");
        lnLabel = new JLabel("Last Name:(Optional) ");
        ageLabel = new JLabel("Approx age(Choose either this or date of birth): ");
        dobLabel = new JLabel("Date of Birth: ");
        genderLabel = new JLabel("Enter your gender:(Optional) ");
        commentsLabel = new JLabel("Any additional comments:(Optional) ");

        //Sets all input titles
        fnInput = new JTextField("e.g Dorothy", 15); 
        lnInput = new JTextField("e.g Gale", 15);
        SpinnerModel ageModel = new SpinnerNumberModel(18, 0, 150, 1);
        ageSpinner = new JSpinner(ageModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(ageSpinner, "#");
        ageSpinner.setEditor(editor);
        dobInput = new JTextField("e.g 2004-01-09", 15);
        genderInput = new JTextField("e.g male", 15);
        commentsInput = new JTextField("e.g Lost in flood", 15);

        //Adds mouse listeners
        fnInput.addMouseListener(this);
        lnInput.addMouseListener(this);
        ageSpinner.addMouseListener(this);
        dobInput.addMouseListener(this);
        genderInput.addMouseListener(this);
        commentsInput.addMouseListener(this);

        //Create buttons
        relationshipsButton = new JButton("Family Relationships");
        medicalRecordsButton = new JButton("Medical Records");
        JButton submitInfo = new JButton("Submit");

        //Main panel that will hold everything:
        JPanel mainPanel = new JPanel(new BorderLayout());

        //Panels for the home page
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        JPanel titlePanel = new JPanel(new GridLayout(6,1));
        JPanel inputPanel = new JPanel(new GridLayout(6,1));
        JPanel overallButtonsPanel = new JPanel(new GridLayout(2,1));
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());

        //Adding all of the components
        headerPanel.add(title);
        titlePanel.add(fnLabel);
        titlePanel.add(lnLabel);
        titlePanel.add(ageLabel);
        titlePanel.add(dobLabel);
        titlePanel.add(genderLabel);
        titlePanel.add(commentsLabel);
        inputPanel.add(fnInput);
        inputPanel.add(lnInput);
        inputPanel.add(ageSpinner);
        inputPanel.add(dobInput);
        inputPanel.add(genderInput);
        inputPanel.add(commentsInput);
        buttonPanel.add(relationshipsButton);
        buttonPanel.add(medicalRecordsButton);
        submitPanel.add(submitInfo);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(titlePanel, BorderLayout.WEST);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        overallButtonsPanel.add(buttonPanel, BorderLayout.NORTH);
        overallButtonsPanel.add(submitPanel, BorderLayout.SOUTH);
        mainPanel.add(overallButtonsPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    public void actionPerformed(ActionEvent event){

    }

    public void mouseClicked(MouseEvent event){

    }

    public void mouseEntered(MouseEvent event){

    }

    public void mouseExited(MouseEvent event){

    }

    public void mousePressed(MouseEvent event){

    }

    public void mouseReleased(MouseEvent event){

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new DisasterVictimLogging().setVisible(true);
        });
    }
    
}
