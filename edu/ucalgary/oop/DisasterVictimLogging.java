package edu.ucalgary.oop;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
//To get current date
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DisasterVictimLogging extends JFrame implements ActionListener, MouseListener{
    private CardLayout cardLayout;
    private JPanel cardPanel;

    /*Main page variables*/
    //All the buttons and labels on the main page
    private JLabel title;
    private JButton relationshipsButton;
    private JButton medicalRecordsButton;
    private JButton submitInfo;

    //Labels for the main page
    private JLabel fnLabel;
    private JLabel lnLabel;
    private JLabel ageLabel;
    private JLabel dobLabel;
    private JLabel genderLabel;
    private JLabel commentsLabel;

    //Input titles for main page
    private JTextField fnInput;
    private JTextField lnInput;
    private JSpinner ageSpinner;
    private JTextField dobInput;
    private JTextField genderInput;
    private JTextField commentsInput;


    /*Medical Record page variables*/
    //Labels for the medical record page
    private JLabel locationLabel;
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel treatmentLabel;
    private JLabel dateOfTreatmentLabel;

    //Inputs for the medical record page
    private JTextField nameInput;
    private JTextField addressInput;
    private JTextField treatmentInput;
    private JTextField dateOfTreatmentInput;

    //Buttons for medical record page
    private JButton backHomeButton;
    private JButton submitMRInfoButton;

    /*Family Relations page variables */


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

        //Add all pages to the card panel:
        cardPanel.add(this.setupMain(), "main");
        cardPanel.add(this.medicalRecordPage(), "medical");

        //Add cardPanel to the main panel
        getContentPane().add(cardPanel);
    }

    private JPanel setupMain(){
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
        submitInfo = new JButton("Submit");

        //Add Button Listeners
        medicalRecordsButton.addActionListener(this);
        relationshipsButton.addActionListener(this);
        submitInfo.addActionListener(this);

        //Panels for the home page
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        JPanel titlePanel = new JPanel(new GridLayout(6,1));
        JPanel inputPanel = new JPanel(new GridLayout(6,1));
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
        buttonPanel.add(submitInfo);

        //Main panel that will hold everything:
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(titlePanel, BorderLayout.WEST);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    public JPanel medicalRecordPage(){
        //Set all labels
        locationLabel = new JLabel("Please enter all location details here: ");
        nameLabel = new JLabel("Name");
        addressLabel = new JLabel("Address");
        treatmentLabel = new JLabel("Enter treatment details");
        dateOfTreatmentLabel = new JLabel("Enter date of treatment");

        //Set all inputs
        nameInput = new JTextField("e.g Telus Spark Centre", 15);
        addressInput = new JTextField("e.g 123 Sesame Street", 15);
        treatmentInput = new JTextField("e.g Wrist Surgery");
        dateOfTreatmentInput = new JTextField("e.g 2022-09-09", 15);

        //Create all buttons
        backHomeButton = new JButton("Home");
        submitMRInfoButton = new JButton("Submit");

        //Create all panels
        JPanel headerPanel = new JPanel(new FlowLayout());
        JPanel contentPanel = new JPanel(new GridLayout(3, 1));
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));

        //Create header panel
        headerPanel.add(title);

        //Create content panel
        JPanel locationPanel = new JPanel(new GridLayout(3, 1));
        locationPanel.add(locationLabel);
        JPanel locationSub1 = new JPanel(new GridLayout(1,2));
        locationSub1.add(nameLabel);
        locationSub1.add(nameInput);
        locationPanel.add(locationSub1);
        JPanel locationSub2 = new JPanel(new GridLayout(1, 2));
        locationSub2.add(addressLabel);
        locationSub2.add(addressInput);
        locationPanel.add(locationSub2);
        JPanel sub1 = new JPanel(new GridLayout(1, 2));
        sub1.add(treatmentLabel);
        sub1.add(treatmentInput);
        JPanel sub2 = new JPanel(new GridLayout(1, 2));
        sub2.add(dateOfTreatmentLabel);
        sub2.add(dateOfTreatmentInput);
        contentPanel.add(locationPanel);
        contentPanel.add(sub1);
        contentPanel.add(sub2);

        //Create buttonsPanel
        buttonPanel.add(backHomeButton);
        buttonPanel.add(submitMRInfoButton);

        //Create final panel
        JPanel medicalRecordPanel = new JPanel();
        medicalRecordPanel.setLayout(new BoxLayout(medicalRecordPanel, BoxLayout.Y_AXIS));
        medicalRecordPanel.add(headerPanel);
        medicalRecordPanel.add(contentPanel);
        medicalRecordPanel.add(buttonPanel); 

        return medicalRecordPanel;
    }

    public void actionPerformed(ActionEvent event){
        //Create the DisasterVictim based on the info entered
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
        ArrayList<FamilyRelation> familyRelations = new ArrayList<>();
        DisasterVictim victim;
        if(event.getSource() == medicalRecordsButton){
            cardLayout.show(cardPanel, "medical");
        }
        if(event.getSource() == submitInfo){
            String fName = fnInput.getText();
            String lName = lnInput.getText();
            Integer age = (Integer) ageSpinner.getValue();
            String dateOfBirth = dobInput.getText();
            String gender = genderInput.getText();
            String comments = commentsInput.getText();
            //Get current date for entry date
            LocalDate currenDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currenDate.format(formatter);

            if(dateOfBirth.equals("") || dateOfBirth.equals("e.g 2004-01-09")){
                victim = new DisasterVictim(fName, formattedDate, age);
                if(!lName.equals("") && !lName.equals("e.g Gale")){
                    victim.setLastName(lName);
                }
                if(!gender.equals("") && !gender.equals("e.g male")){
                    victim.setGender(gender);
                }
                if(!comments.equals("") && !comments.equals("e.g Lost in flood")){
                    victim.setComments(comments);
                }
            }
            else{
                victim = new DisasterVictim(fName, formattedDate, dateOfBirth);
                if(!lName.equals("") && !lName.equals("e.g Gale")){
                    victim.setLastName(lName);
                }
                if(!gender.equals("") && !gender.equals("e.g male")){
                    victim.setGender(gender);
                }
                if(!comments.equals("") && !comments.equals("e.g Lost in flood")){
                    victim.setComments(comments);
                }
            }
            victim.setFamilyConnections(familyRelations);
            victim.setMedicalRecords(medicalRecords);
            System.out.println(comments);
        }
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
