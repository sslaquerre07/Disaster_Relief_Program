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
    private JLabel dietaryLabel;
    private JLabel commentsLabel;

    //Input titles for main page
    private JTextField fnInput;
    private JTextField lnInput;
    private JSpinner ageSpinner;
    private JTextField dobInput;
    private JTextField genderInput;
    private JScrollPane dietaryInput;
    private DefaultListModel<DietaryRestriction> listModel = new DefaultListModel<>();
    private JList<DietaryRestriction> list = new JList<>(listModel);
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
    private JButton backHomeButtonMR;
    private JButton submitMRInfoButton;

    /*Family Relations page variables */

    //Labels for the family relations page
    private JLabel fnLabelFR;
    private JLabel lnLabelFR;
    private JLabel ageLabelFR;
    private JLabel dobLabelFR;
    private JLabel genderLabelFR;
    private JLabel dietaryLabelFR;
    private JLabel commentsLabelFR;
    private JLabel relationshipToLabel;

    //Input titles for main page
    private JTextField fnInputFR;
    private JTextField lnInputFR;
    private JSpinner ageSpinnerFR;
    private JTextField dobInputFR;
    private JTextField genderInputFR;
    private JScrollPane dietaryInputFR;
    private JTextField commentsInputFR;
    private JTextField relationshipToInput;

    //Extra button for relations
    private JButton backHomeButtonFR; 
    private JButton submitRelations;


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
        cardPanel.add(this.familyRelationsPage(), "relation");

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
        dietaryLabel = new JLabel("<html>Please select all necessary dietary restrictions<br />Hold the CTRL key while selecting if you have multiple</html>");
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
        DefaultListModel<DietaryRestriction> listModel = new DefaultListModel<>();
        JList<DietaryRestriction> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //Unfortunately, users will have to hold the control button to multi-select
        dietaryInput = new JScrollPane(list);
        listModel.addElement(DietaryRestriction.AVML);
        listModel.addElement(DietaryRestriction.DBML);
        listModel.addElement(DietaryRestriction.GFML);
        listModel.addElement(DietaryRestriction.KSML);
        listModel.addElement(DietaryRestriction.LSML);
        listModel.addElement(DietaryRestriction.MOML);
        listModel.addElement(DietaryRestriction.PFML);
        listModel.addElement(DietaryRestriction.VGML);
        listModel.addElement(DietaryRestriction.VJML);
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
        JPanel titlePanel = new JPanel(new GridLayout(7,1));
        JPanel inputPanel = new JPanel(new GridLayout(7,1));
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
        titlePanel.add(dietaryLabel);
        titlePanel.add(commentsLabel);
        inputPanel.add(fnInput);
        inputPanel.add(lnInput);
        inputPanel.add(ageSpinner);
        inputPanel.add(dobInput);
        inputPanel.add(genderInput);
        inputPanel.add(dietaryInput);
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

    private JPanel medicalRecordPage(){
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
        backHomeButtonMR = new JButton("Home");
        submitMRInfoButton = new JButton("Submit");

        //Add all listeners
        nameInput.addMouseListener(this);
        addressInput.addMouseListener(this);
        treatmentInput.addMouseListener(this);
        dateOfTreatmentInput.addMouseListener(this);
        backHomeButtonMR.addActionListener(this);
        submitMRInfoButton.addActionListener(this);

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
        buttonPanel.add(backHomeButtonMR);
        buttonPanel.add(submitMRInfoButton);

        //Create final panel
        JPanel medicalRecordPanel = new JPanel();
        medicalRecordPanel.setLayout(new BoxLayout(medicalRecordPanel, BoxLayout.Y_AXIS));
        medicalRecordPanel.add(headerPanel);
        medicalRecordPanel.add(contentPanel);
        medicalRecordPanel.add(buttonPanel); 

        return medicalRecordPanel;
    }

    private JPanel familyRelationsPage(){
        title = new JLabel("Family Relations Page");

        //Set all labels
        fnLabelFR = new JLabel("First Name: ");
        lnLabelFR = new JLabel("Last Name:(Optional) ");
        ageLabelFR = new JLabel("Approx age(Choose either this or date of birth): ");
        dobLabelFR = new JLabel("Date of Birth: ");
        genderLabelFR = new JLabel("Enter your gender:(Optional) ");
        commentsLabelFR = new JLabel("Any additional comments:(Optional) ");
        relationshipToLabel = new JLabel("Relationship to the victim");

        //Sets all input titles
        fnInputFR = new JTextField("e.g Dorothy", 15); 
        lnInputFR = new JTextField("e.g Gale", 15);
        SpinnerModel ageModel = new SpinnerNumberModel(18, 0, 150, 1);
        ageSpinnerFR = new JSpinner(ageModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(ageSpinnerFR, "#");
        ageSpinnerFR.setEditor(editor);
        dobInputFR = new JTextField("e.g 2004-01-09", 15);
        genderInputFR = new JTextField("e.g male", 15);
        commentsInputFR = new JTextField("e.g Lost in flood", 15);
        relationshipToInput = new JTextField("e.g sibling");

        //Adds mouse listeners
        fnInputFR.addMouseListener(this);
        lnInputFR.addMouseListener(this);
        ageSpinnerFR.addMouseListener(this);
        dobInputFR.addMouseListener(this);
        genderInputFR.addMouseListener(this);
        commentsInputFR.addMouseListener(this);

        //Add Button
        submitRelations = new JButton("Submit");
        backHomeButtonFR = new JButton("Home");

        //Add Button Listeners
        submitRelations.addActionListener(this);
        backHomeButtonFR.addActionListener(this);

        //Panels for the home page
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        JPanel titlePanel = new JPanel(new GridLayout(7,1));
        JPanel inputPanel = new JPanel(new GridLayout(7,1));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());

        //Adding all of the components
        headerPanel.add(title);
        titlePanel.add(fnLabelFR);
        titlePanel.add(lnLabelFR);
        titlePanel.add(ageLabelFR);
        titlePanel.add(dobLabelFR);
        titlePanel.add(genderLabelFR);
        titlePanel.add(commentsLabelFR);
        titlePanel.add(relationshipToLabel);
        inputPanel.add(fnInputFR);
        inputPanel.add(lnInputFR);
        inputPanel.add(ageSpinnerFR);
        inputPanel.add(dobInputFR);
        inputPanel.add(genderInputFR);
        inputPanel.add(commentsInputFR);
        inputPanel.add(relationshipToInput);
        buttonPanel.add(submitRelations);
        buttonPanel.add(backHomeButtonFR);

        //Main panel that will hold everything:
        JPanel relationPanel = new JPanel(new BorderLayout());
        relationPanel.add(headerPanel, BorderLayout.NORTH);
        relationPanel.add(titlePanel, BorderLayout.WEST);
        relationPanel.add(inputPanel, BorderLayout.CENTER);
        relationPanel.add(buttonPanel, BorderLayout.SOUTH);

        return relationPanel;
    }

    public void actionPerformed(ActionEvent event){
        /*Brief summary of what it's doing right now
         * There is no instruction for how to check a stored location
         * so we're just creating a new instance for every time you 
         * utilize the interface!!
         */
        
        //Create the DisasterVictim based on the info entered
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
        ArrayList<DisasterVictim> relations = new ArrayList<>();
        ArrayList<String> relationships = new ArrayList<>();
        ArrayList<FamilyRelation> familyRelations = new ArrayList<>();
        DisasterVictim victim;
        //All navigational buttons
        if(event.getSource() == medicalRecordsButton){
            cardLayout.show(cardPanel, "medical");
        }
        if(event.getSource() == backHomeButtonMR || event.getSource() == backHomeButtonFR){
            cardLayout.show(cardPanel, "main");
        }
        if(event.getSource() == relationshipsButton){
            cardLayout.show(cardPanel, "relation");
        }

        //All information retrieving buttons
        if(event.getSource() == submitInfo){
            boolean validInfo = true;
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

            //Either age or dateofBirth
            if(dateOfBirth.equals("") || dateOfBirth.equals("e.g 2004-01-09") || !DisasterVictim.isValidDateFormat(dateOfBirth)){
                victim = new DisasterVictim(fName, formattedDate, age);
            }
            else{
                victim = new DisasterVictim(fName, formattedDate, dateOfBirth);
            }
            //Data validation
            if(!fName.trim().equals("") && !fName.trim().equals("e.g Dorothy")){
                victim.setLastName(lName);
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid first name");
                validInfo = false;
            }
            if(!lName.trim().equals("") && !lName.trim().equals("e.g Gale")){
                victim.setLastName(lName);
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid last name");
                validInfo = false;
            }
            if(!gender.trim().equals("") && !gender.trim().equals("e.g male") && victim.validGender(gender)){
                victim.setGender(gender);
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid gender");
                validInfo = false;
            }
            if(!comments.trim().equals("") && !comments.trim().equals("e.g Lost in flood")){
                victim.setComments(comments);
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid comments");
                validInfo = false;
            }
            //Retrieve all dietary restrictions
            ArrayList<DietaryRestriction> selectedOptions = new ArrayList<>();
            int [] selectedIndices = list.getSelectedIndices();
            for(int index : selectedIndices){
                selectedOptions.add(listModel.getElementAt(index));
            }
            for(DietaryRestriction restriction : selectedOptions){
                victim.addDietaryRestriction(restriction);
            }
            //Create all family connections from the list of relatives
            for(int i = 0; i < relations.size(); i++){
                familyRelations.add(new FamilyRelation(victim, relationships.get(i), relations.get(i)));
            }
            victim.setFamilyConnections(familyRelations);
            victim.setMedicalRecords(medicalRecords);
            if(validInfo){
                JOptionPane.showMessageDialog(this, "DisasterVictim created successfully!");
                System.exit(0);
            }
        }

        if(event.getSource() == submitMRInfoButton){
            boolean validData = true;
            Location location = null;
            String locationName = nameInput.getText();
            String locationAddress = addressInput.getText();
            String treatmentDetailString = treatmentInput.getText();
            String dateOfTreatmentString = dateOfTreatmentInput.getText();

            //Confirmation of Location
            if(!locationName.trim().equals("") && !locationName.trim().equals("e.g Telus Spark Centre")){
                if(!locationAddress.trim().equals("") && !locationAddress.trim().equals("e.g 123 Sesame Street")){
                    location = new Location(locationName, locationAddress);
                }
                else{
                    JOptionPane.showMessageDialog(this, "Invalid Location Address");
                    validData = false;
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid Location Name");
                validData = false;
            }
            //Validity of inputs
            if(treatmentDetailString.trim().equals("") || treatmentDetailString.trim().equals("e.g Wrist Surgery")){
                JOptionPane.showMessageDialog(this, "Invalid treatment details");
                validData = false;
            }
            if(dateOfTreatmentString.trim().equals("") || dateOfTreatmentString.trim().equals("e.g 2022-09-09") || !DisasterVictim.isValidDateFormat(dateOfTreatmentString)){
                JOptionPane.showMessageDialog(this, "Invalid treatment date");
                validData = false;
            }
            //Adds it to the arrayList if all inputs are valid
            if(validData && location != null){
                medicalRecords.add(new MedicalRecord(location, treatmentDetailString, dateOfTreatmentString));
                JOptionPane.showMessageDialog(this, "Medical Record created and added successfully");
                cardLayout.show(cardPanel, "main");
            }
        }

        if(event.getSource() == submitRelations){
            DisasterVictim newRelation;
            boolean validInfo = true;
            String fName = fnInputFR.getText();
            String lName = lnInputFR.getText();
            Integer age = (Integer) ageSpinnerFR.getValue();
            String dateOfBirth = dobInputFR.getText();
            String gender = genderInputFR.getText();
            String comments = commentsInputFR.getText();
            //Get current date for entry date
            LocalDate currenDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currenDate.format(formatter);

            //Either age or dateofBirth
            if(dateOfBirth.equals("") || dateOfBirth.equals("e.g 2004-01-09") || !DisasterVictim.isValidDateFormat(dateOfBirth)){
                newRelation = new DisasterVictim(fName, formattedDate, age);
            }
            else{
                newRelation = new DisasterVictim(fName, formattedDate, dateOfBirth);
            }
            //Data validation
            if(!fName.trim().equals("") && !fName.trim().equals("e.g Dorothy")){
                newRelation.setLastName(lName);
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid first name");
                validInfo = false;
            }
            if(!lName.trim().equals("") && !lName.trim().equals("e.g Gale")){
                newRelation.setLastName(lName);
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid last name");
                validInfo = false;
            }
            if(!gender.trim().equals("") && !gender.trim().equals("e.g male") && newRelation.validGender(gender)){
                newRelation.setGender(gender);
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid gender");
                validInfo = false;
            }
            if(!comments.trim().equals("") && !comments.trim().equals("e.g Lost in flood")){
                newRelation.setComments(comments);
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid comments");
                validInfo = false;
            }
            if(validInfo){
                JOptionPane.showMessageDialog(this, "FamilyRelation created successfully!");
                relations.add(newRelation);
                relationships.add(relationshipToInput.getText());
                cardLayout.show(cardPanel, "main");
            }
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
