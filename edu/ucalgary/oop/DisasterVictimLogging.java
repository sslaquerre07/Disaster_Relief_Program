package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
//To get current date
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DisasterVictimLogging extends JFrame implements ActionListener{
    private boolean centralWorkerFlag;
    private Integer locationID;
    private boolean inquiryOpen;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private DBAccess dbConnect;

    //All pages reference
    private JPanel mainPanel;
    private JPanel medicalPanel;
    private JPanel familyRelationsPanel;

    //Declaration of variables for data processing
    //Create the DisasterVictim based on the info entered
    private ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
    private ArrayList<DisasterVictim> relations = new ArrayList<>();
    private ArrayList<String> relationships = new ArrayList<>();
    private ArrayList<FamilyRelation> familyRelations = new ArrayList<>();
    private DisasterVictim victim;

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
    private JLabel locationIDLabel;
    private JLabel genderLabel;
    private JLabel dietaryLabel;
    private JLabel commentsLabel;

    //Input titles for main page
    private JTextField fnInput;
    private JTextField lnInput;
    private JSpinner ageSpinner;
    private JTextField dobInput;
    private JSpinner locationIDSpinner;
    private JTextField genderInput;
    private JScrollPane dietaryInput;
    private DefaultListModel<DietaryRestriction> listModel = new DefaultListModel<>();
    private JList<DietaryRestriction> list = new JList<>(listModel);
    private JTextField commentsInput;


    /*Medical Record page variables*/
    //Labels for the medical record page
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel treatmentLabel;
    private JLabel dateOfTreatmentLabel;

    //Inputs for the medical record page
    private JRadioButtonMenuItem currentLocation;
    private JRadioButtonMenuItem newLocation;
    private JPanel newLocationJPanel;
    private JPanel existingLocationJPanel;
    private JPanel locationSelection;
    private JTextField nameInput;
    private JTextField addressInput;
    private JTextField treatmentInput;
    private JTextField dateOfTreatmentInput;
    private JScrollPane resultsMR;
    private DefaultListModel<String> listModelMR = new DefaultListModel<>();
    private JList<String> listMR = new JList<>(listModelMR);

    //Buttons for medical record page
    private JRadioButtonMenuItem currentVictim;
    private JRadioButtonMenuItem newVictim;
    private JButton backHomeButtonMR;
    private JButton submitMRInfoButton;

    /*Family Relations page variables */

    //Labels for the family relations page
    private JPanel victimSelection;
    private JPanel centerPanel;
    private JLabel fnLabelFR;
    private JLabel lnLabelFR;
    private JLabel ageLabelFR;
    private JLabel dobLabelFR;
    private JLabel genderLabelFR;
    private JLabel dietaryLabelFR;
    private JLabel locationLabelFR;
    private JLabel commentsLabelFR;
    private JLabel relationshipToLabel;

    //Input titles for main page
    private JTextField fnInputFR;
    private JTextField lnInputFR;
    private JSpinner ageSpinnerFR;
    private JTextField dobInputFR;
    private JTextField genderInputFR;
    private JScrollPane dietaryInputFR;
    private DefaultListModel<DietaryRestriction> listModelFR = new DefaultListModel<>();
    private JSpinner locationIDSpinnerFR;
    private JList<DietaryRestriction> listFR = new JList<>(listModelFR);
    private JTextField commentsInputFR;
    private JTextField relationshipToInput;
    private JScrollPane victimResults;
    private DefaultListModel<String> victimModel = new DefaultListModel<>();
    private JList<String> victimList = new JList<>(listModelMR);

    //Extra button for relations
    private JButton backHomeButtonFR; 
    private JButton submitRelations;

    //For central workers
    public DisasterVictimLogging(){
        super("Disaster Victim Logging GUI");
        this.dbConnect = new DBAccess();
        this.centralWorkerFlag = true;

        setupGUI(this.centralWorkerFlag);
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    //For location workers
    public DisasterVictimLogging(Integer locationID){
        super("Disaster Victim Logging GUI");
        this.dbConnect = new DBAccess();
        this.locationID = locationID;
        this.centralWorkerFlag = false;

        setupGUI(this.centralWorkerFlag);
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void setInquiryOpen(boolean status){
        this.inquiryOpen = status;
    }

    public boolean getInquiryOpen(){
        return this.inquiryOpen;
    }

    public void setupGUI(boolean centralWorkerFlag){
        this.inquiryOpen = false;
        //Creates the layout to switch between pages
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);
        this.mainPanel = this.setupMain(centralWorkerFlag);
        this.medicalPanel = this.medicalRecordPage();
        this.familyRelationsPanel = this.familyRelationsPage();

        //Add all pages to the card panel:
        this.cardPanel.add(this.mainPanel, "main");
        this.cardPanel.add(this.medicalPanel, "medical");
        this.cardPanel.add(this.familyRelationsPanel, "relation");

        //Add cardPanel to the main panel
        getContentPane().add(this.cardPanel);
    }

    /*Main page related functions */
    private JPanel setupMain(boolean centralWorkerFlag){
        this.title = new JLabel("Welcome to the Disaster Victim Logging Page");

        //Set all labels
        this.fnLabel = new JLabel("First Name: ");
        this.lnLabel = new JLabel("Last Name:(Optional) ");
        this.ageLabel = new JLabel("Approx age(Choose either this or date of birth): ");
        this.dobLabel = new JLabel("Date of Birth: ");
        if(centralWorkerFlag){
            this.locationIDLabel = new JLabel("Please enter the location ID");
        }
        this.genderLabel = new JLabel("Enter your gender:(Optional) ");
        this.dietaryLabel = new JLabel("<html>Please select all necessary dietary restrictions<br />Hold the CTRL key while selecting if you have multiple</html>");
        this.commentsLabel = new JLabel("Any additional comments:(Optional) ");

        //Sets all input titles
        this.fnInput = new JTextField("e.g Dorothy", 15); 
        this.lnInput = new JTextField("e.g Gale", 15);
        SpinnerModel ageModel = new SpinnerNumberModel(18, 0, 150, 1);
        this.ageSpinner = new JSpinner(ageModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(this.ageSpinner, "#");
        this.ageSpinner.setEditor(editor);
        this.dobInput = new JTextField("e.g 2004-01-09", 15);
        if(centralWorkerFlag){
            SpinnerModel locationID = new SpinnerNumberModel(1, 0, 150, 1);
            this.locationIDSpinner = new JSpinner(locationID);
            JSpinner.NumberEditor editorL = new JSpinner.NumberEditor(this.locationIDSpinner, "#");
            this.locationIDSpinner.setEditor(editorL);
        }
        this.genderInput = new JTextField("e.g male", 15);
        this.listModel = new DefaultListModel<>();
        this.list = new JList<>(this.listModel);
        this.list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //Unfortunately, users will have to hold the control button to multi-select
        this.dietaryInput = new JScrollPane(this.list);
        this.listModel.addElement(DietaryRestriction.AVML);
        this.listModel.addElement(DietaryRestriction.DBML);
        this.listModel.addElement(DietaryRestriction.GFML);
        this.listModel.addElement(DietaryRestriction.KSML);
        this.listModel.addElement(DietaryRestriction.LSML);
        this.listModel.addElement(DietaryRestriction.MOML);
        this.listModel.addElement(DietaryRestriction.PFML);
        this.listModel.addElement(DietaryRestriction.VGML);
        this.listModel.addElement(DietaryRestriction.VJML);
        this.commentsInput = new JTextField("e.g Lost in flood", 15);

        //Create buttons
        this.relationshipsButton = new JButton("Family Relationships");
        this.medicalRecordsButton = new JButton("Medical Records");
        this.submitInfo = new JButton("Submit");

        //Add Button Listeners
        this.medicalRecordsButton.addActionListener(this);
        this.relationshipsButton.addActionListener(this);
        this.submitInfo.addActionListener(this);

        //Panels for the home page
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        JPanel titlePanel = new JPanel(new GridLayout(8,1));
        JPanel inputPanel = new JPanel(new GridLayout(8,1));
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());

        //Adding all of the components
        headerPanel.add(this.title);
        titlePanel.add(this.fnLabel);
        titlePanel.add(this.lnLabel);
        titlePanel.add(this.ageLabel);
        titlePanel.add(this.dobLabel);
        if(centralWorkerFlag){
            titlePanel.add(this.locationIDLabel);
        }
        titlePanel.add(this.genderLabel);
        titlePanel.add(this.dietaryLabel);
        titlePanel.add(this.commentsLabel);
        inputPanel.add(this.fnInput);
        inputPanel.add(this.lnInput);
        inputPanel.add(this.ageSpinner);
        inputPanel.add(this.dobInput);
        if(centralWorkerFlag){
            inputPanel.add(this.locationIDSpinner);
        }
        inputPanel.add(this.genderInput);
        inputPanel.add(this.dietaryInput);
        inputPanel.add(this.commentsInput);
        buttonPanel.add(this.relationshipsButton);
        buttonPanel.add(this.medicalRecordsButton);
        buttonPanel.add(this.submitInfo);

        //Main panel that will hold everything:
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(titlePanel, BorderLayout.WEST);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    /*Medical Record page related functions */
    private JPanel medicalRecordPage(){
        //Set all inputs
        this.locationSelection = new JPanel(new GridLayout(1, 2));
        ButtonGroup group = new ButtonGroup();
        this.currentLocation = new JRadioButtonMenuItem("Current Location");
        this.newLocation = new JRadioButtonMenuItem("Create a new location");
        group.add(this.currentLocation);
        group.add(this.newLocation);
        this.locationSelection.add(this.currentLocation);
        this.locationSelection.add(this.newLocation);
        this.newLocationJPanel = this.newLocationPanel();

        //Create all buttons
        this.backHomeButtonMR = new JButton("Home");
        this.submitMRInfoButton = new JButton("Submit");

        //Add action listeners
        this.backHomeButtonMR.addActionListener(this);
        this.submitMRInfoButton.addActionListener(this);
        this.currentLocation.addActionListener(this);
        this.newLocation.addActionListener(this);

        //Create all panels
        JPanel headerPanel = new JPanel(new FlowLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));

        //Create header panel
        headerPanel.add(this.title);

        //Create buttonsPanel
        buttonPanel.add(this.backHomeButtonMR);
        buttonPanel.add(this.submitMRInfoButton);

        //Create final panel
        JPanel medicalRecordPanel = new JPanel(new GridLayout(4, 1));
        medicalRecordPanel.add(headerPanel);
        medicalRecordPanel.add(this.locationSelection);
        medicalRecordPanel.add(new JPanel());
        this.newLocationPanel().setVisible(false);
        medicalRecordPanel.add(buttonPanel); 

        return medicalRecordPanel;
    }

    private JPanel newLocationPanel(){
        this.nameLabel = new JLabel("Location Name");
        this.addressLabel = new JLabel("Location Address");
        this.treatmentLabel = new JLabel("Enter treatment details");
        this.dateOfTreatmentLabel = new JLabel("Enter date of treatment");

        this.nameInput = new JTextField("e.g Telus Spark Centre", 15);
        this.addressInput = new JTextField("e.g 123 Sesame Street", 15);
        this.treatmentInput = new JTextField("e.g Wrist Surgery");
        this.dateOfTreatmentInput = new JTextField("e.g 2022-09-09", 15);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridLayout(4, 1));
        JPanel contentPanel = new JPanel(new GridLayout(4, 1));

        labelPanel.add(this.nameLabel);
        labelPanel.add(this.addressLabel);
        labelPanel.add(this.dateOfTreatmentLabel);
        labelPanel.add(this.treatmentLabel);
        contentPanel.add(this.nameInput);
        contentPanel.add(this.addressInput);
        contentPanel.add(this.dateOfTreatmentInput);
        contentPanel.add(this.treatmentInput);
        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel existingLocationPanel(){
        //Treatment details and date
        this.treatmentLabel = new JLabel("Enter treatment details");
        this.dateOfTreatmentLabel = new JLabel("Enter date of treatment");
        this.treatmentInput = new JTextField("e.g Wrist Surgery");
        this.dateOfTreatmentInput = new JTextField("e.g 2022-09-09", 15);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.add(this.dateOfTreatmentLabel);
        labelPanel.add(this.treatmentLabel);
        contentPanel.add(this.dateOfTreatmentInput);
        contentPanel.add(this.treatmentInput);
        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(contentPanel, BorderLayout.CENTER);

        //Location setup
        this.existingLocationJPanel = new JPanel(new GridLayout(2,1));
        this.listModelMR = new DefaultListModel<>();
        this.listMR = new JList<>(this.listModelMR);
        this.listMR.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.resultsMR = new JScrollPane(this.listMR);
        ResultSet locations = this.dbConnect.retrieveAllLocations();
        try{
            while(locations.next()){
                String locationName = locations.getString("name");
                this.listModelMR.addElement(locationName);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        this.existingLocationJPanel.add(this.resultsMR);
        this.existingLocationJPanel.add(panel);
        return this.existingLocationJPanel;
    }

    /*Family Relation page related functions */
    private JPanel familyRelationsPage(){
        //Add all inputs and titles
        this.title = new JLabel("Family Relations Page");
        this.victimSelection = new JPanel(new GridLayout(1, 2));
        this.centerPanel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        this.currentVictim = new JRadioButtonMenuItem("Choose from existing Victims");
        this.newVictim = new JRadioButtonMenuItem("Create a new vicitm");
        group.add(this.currentVictim);
        group.add(this.newVictim);
        this.victimSelection.add(this.currentVictim);
        this.victimSelection.add(this.newVictim);

        //Add Button
        this.submitRelations = new JButton("Submit");
        this.backHomeButtonFR = new JButton("Home");

        //Add Button Listeners
        this.currentVictim.addActionListener(this);
        this.newVictim.addActionListener(this);
        this.submitRelations.addActionListener(this);
        this.backHomeButtonFR.addActionListener(this);

        //Panels for the home page
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());

        //Adding all of the components
        headerPanel.add(this.title);
        headerPanel.add(this.victimSelection);
        buttonPanel.add(this.submitRelations);
        buttonPanel.add(this.backHomeButtonFR);

        //Main panel that will hold everything:
        JPanel relationPanel = new JPanel(new BorderLayout());
        relationPanel.add(headerPanel, BorderLayout.NORTH);
        relationPanel.add(this.centerPanel, BorderLayout.CENTER);
        relationPanel.add(buttonPanel, BorderLayout.SOUTH);

        return relationPanel;
    }

    private JPanel newPersonPanel(){
        //Set all labels
        this.fnLabelFR = new JLabel("First Name: ");
        this.lnLabelFR = new JLabel("Last Name:(Optional) ");
        this.ageLabelFR = new JLabel("Approx age(Choose either this or date of birth): ");
        this.dobLabelFR = new JLabel("Date of Birth: ");
        this.genderLabelFR = new JLabel("Enter your gender:(Optional) ");
        this.dietaryLabelFR = new JLabel("<html>Please select all necessary dietary restrictions<br />Hold the CTRL key while selecting if you have multiple</html>");
        this.locationLabelFR = new JLabel("Choose location ID");
        this.commentsLabelFR = new JLabel("Any additional comments:(Optional) ");
        this.relationshipToLabel = new JLabel("Relationship to the victim");

        //Sets all input titles
        this.fnInputFR = new JTextField("e.g Dorothy", 15); 
        this.lnInputFR = new JTextField("e.g Gale", 15);
        SpinnerModel ageModel = new SpinnerNumberModel(18, 0, 150, 1);
        this.ageSpinnerFR = new JSpinner(ageModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(this.ageSpinnerFR, "#");
        this.ageSpinnerFR.setEditor(editor);
        this.dobInputFR = new JTextField("e.g 2004-01-09", 15);
        this.genderInputFR = new JTextField("e.g male", 15);
        this.listFR.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //Unfortunately, users will have to hold the control button to multi-select
        this.dietaryInputFR = new JScrollPane(this.listFR);
        this.listModelFR.addElement(DietaryRestriction.AVML);
        this.listModelFR.addElement(DietaryRestriction.DBML);
        this.listModelFR.addElement(DietaryRestriction.GFML);
        this.listModelFR.addElement(DietaryRestriction.KSML);
        this.listModelFR.addElement(DietaryRestriction.LSML);
        this.listModelFR.addElement(DietaryRestriction.MOML);
        this.listModelFR.addElement(DietaryRestriction.PFML);
        this.listModelFR.addElement(DietaryRestriction.VGML);
        this.listModelFR.addElement(DietaryRestriction.VJML);
        SpinnerModel locationIDFR = new SpinnerNumberModel(1, 0, 150, 1);
        this.locationIDSpinnerFR = new JSpinner(locationIDFR);
        JSpinner.NumberEditor editorFR = new JSpinner.NumberEditor(this.locationIDSpinnerFR, "#");
        this.locationIDSpinnerFR.setEditor(editorFR);
        this.commentsInputFR = new JTextField("e.g Lost in flood", 15);
        this.relationshipToInput = new JTextField("e.g sibling", 15);

        JPanel titlePanel = new JPanel(new GridLayout(9,1));
        JPanel inputPanel = new JPanel(new GridLayout(9,1));

        titlePanel.add(this.fnLabelFR);
        titlePanel.add(this.lnLabelFR);
        titlePanel.add(this.ageLabelFR);
        titlePanel.add(this.dobLabelFR);
        titlePanel.add(this.genderLabelFR);
        titlePanel.add(this.dietaryLabelFR);
        titlePanel.add(this.locationLabelFR);
        titlePanel.add(this.commentsLabelFR);
        titlePanel.add(this.relationshipToLabel);
        inputPanel.add(this.fnInputFR);
        inputPanel.add(this.lnInputFR);
        inputPanel.add(this.ageSpinnerFR);
        inputPanel.add(this.dobInputFR);
        inputPanel.add(this.genderInputFR);
        inputPanel.add(this.dietaryInputFR);
        inputPanel.add(this.locationIDSpinnerFR);
        inputPanel.add(this.commentsInputFR);
        inputPanel.add(this.relationshipToInput);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titlePanel, BorderLayout.WEST);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel existingPerson(){
        JPanel existingPersonPanel = new JPanel();
        existingPersonPanel.setLayout(new BoxLayout(existingPersonPanel, BoxLayout.Y_AXIS));
        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        this.victimModel = new DefaultListModel<>();
        this.victimList = new JList<>(this.victimModel);
        this.victimList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.victimResults = new JScrollPane(this.victimList);
        this.relationshipToLabel = new JLabel("Relationship to the victim");
        this.relationshipToInput = new JTextField("e.g sibling", 15);
        ResultSet victims = this.dbConnect.retrieveAllDisasterVictims();
        try{
            while(victims.next()){
                String victimName = victims.getString("fname") + " " + victims.getString("lname");
                this.victimModel.addElement(victimName);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        existingPersonPanel.add(this.victimResults);
        contentPanel.add(this.relationshipToLabel);
        contentPanel.add(this.relationshipToInput);
        existingPersonPanel.add(contentPanel);
        return existingPersonPanel;
    }

    /*Handle all button presses and option selections */
    public void actionPerformed(ActionEvent event){       
        //All navigational buttons
        if(event.getSource() == this.medicalRecordsButton){
            this.cardLayout.show(this.cardPanel, "medical");
        }
        if(event.getSource() == this.backHomeButtonMR || event.getSource() == this.backHomeButtonFR){
            this.cardLayout.show(this.cardPanel, "main");
        }
        if(event.getSource() == this.relationshipsButton){
            this.cardLayout.show(this.cardPanel, "relation");
        }

        //All information retrieving buttons
        if(event.getSource() == this.submitInfo){
            boolean validInfo = true;
            String fName = this.fnInput.getText();
            String lName = this.lnInput.getText();
            Integer age = (Integer) this.ageSpinner.getValue();
            String dateOfBirth = this.dobInput.getText();
            String gender = this.genderInput.getText();
            String comments = this.commentsInput.getText();
            //Get current date for entry date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);

            //Either age or dateofBirth
            if(dateOfBirth.equals("") || dateOfBirth.equals("e.g 2004-01-09") || !DisasterVictim.isValidDateFormat(dateOfBirth)){
                this.victim = new DisasterVictim(fName, formattedDate, age);
            }
            else{
                this.victim = new DisasterVictim(fName, formattedDate, dateOfBirth);
            }
            //Data validation
            if(!fName.trim().equals("") && !fName.trim().equals("e.g Dorothy")){
                this.victim.setLastName(lName);
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid first name");
                validInfo = false;
            }
            if(!lName.trim().equals("") && !lName.trim().equals("e.g Gale")){
                this.victim.setLastName(lName);
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid last name");
                validInfo = false;
            }
            if(!gender.trim().equals("") && !gender.trim().equals("e.g male") && this.victim.validGender(gender)){
                this.victim.setGender(gender);
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid gender");
                validInfo = false;
            }
            if(!comments.trim().equals("") && !comments.trim().equals("e.g Lost in flood")){
                this.victim.setComments(comments);
            }
            //Retrieve all dietary restrictions
            ArrayList<DietaryRestriction> selectedOptions = new ArrayList<>();
            int [] selectedIndices = this.list.getSelectedIndices();
            for(int index : selectedIndices){
                selectedOptions.add(this.listModel.getElementAt(index));
            }
            for(DietaryRestriction restriction : selectedOptions){
                this.victim.addDietaryRestriction(restriction);
            }
            //Create all family connections from the list of relatives
            for(int i = 0; i < this.relations.size(); i++){
                this.familyRelations.add(new FamilyRelation(this.victim, this.relationships.get(i), this.relations.get(i)));
            }
            this.victim.setFamilyConnections(this.familyRelations);
            this.victim.setMedicalRecords(this.medicalRecords);
            if(centralWorkerFlag){
                if(!this.dbConnect.validLocationID((int) this.locationIDSpinner.getValue())){
                    JOptionPane.showMessageDialog(this, "Invalid Location ID");
                    validInfo = false;
                }
            }
            
            if(validInfo){
                if(this.centralWorkerFlag){
                    this.dbConnect.addDisasterVictim(this.victim, (int) this.locationIDSpinner.getValue(), this.relations);
                }
                else{
                    this.dbConnect.addDisasterVictim(this.victim, this.locationID, this.relations);
                }
                JOptionPane.showMessageDialog(this, "DisasterVictim created successfully!");
                this.dbConnect.close();
                if(this.getInquiryOpen()){
                    dispose();
                }
                else{
                    System.exit(0);
                }
            }
        }

        /* Medical Records Related Info */
        if(event.getSource() == this.submitMRInfoButton && this.newLocation.isSelected()){
            boolean validData = true;
            Location location = null;
            String locationName = this.nameInput.getText();
            String locationAddress = this.addressInput.getText();
            String treatmentDetailString = this.treatmentInput.getText();
            String dateOfTreatmentString = this.dateOfTreatmentInput.getText();

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
                this.dbConnect.addLocation(location);
                this.medicalRecords.add(new MedicalRecord(location, treatmentDetailString, dateOfTreatmentString));
                JOptionPane.showMessageDialog(this, "Medical Record created and added successfully");
                this.cardLayout.show(this.cardPanel, "main");
            }
        }
        if (event.getSource() == this.submitMRInfoButton && this.currentLocation.isSelected()){
            boolean validData = true;
            String treatmentDetailString = this.treatmentInput.getText();
            String dateOfTreatmentString = this.dateOfTreatmentInput.getText();
            int selectedIndex = this.listMR.getSelectedIndex();

            //Validity of inputs
            if(treatmentDetailString.trim().equals("") || treatmentDetailString.trim().equals("e.g Wrist Surgery")){
                JOptionPane.showMessageDialog(this, "Invalid treatment details");
                validData = false;
            }
            if(dateOfTreatmentString.trim().equals("") || dateOfTreatmentString.trim().equals("e.g 2022-09-09") || !DisasterVictim.isValidDateFormat(dateOfTreatmentString)){
                JOptionPane.showMessageDialog(this, "Invalid treatment date");
                validData = false;
            }
            if(selectedIndex == -1){
                JOptionPane.showMessageDialog(this, "Please select a location");
                validData = false;
            }

            if(validData){
                //Retrieve selected location data
                String locationName = this.listMR.getModel().getElementAt(selectedIndex);
                ResultSet locationInfo = this.dbConnect.retrieveLocation(locationName);
                try{
                    if(locationInfo.next()){
                        this.medicalRecords.add(new MedicalRecord(new Location(locationName, locationInfo.getString("address")), treatmentDetailString, dateOfTreatmentString));
                        JOptionPane.showMessageDialog(this, "Medical Record created and added successfully");
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Something went wrong with creating your medical record");
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
                this.cardLayout.show(this.cardPanel, "main");
            }
        }

        if(event.getSource() == this.newLocation){
            this.newLocationJPanel = newLocationPanel();
            this.medicalPanel.remove(2);
            this.medicalPanel.add(this.newLocationJPanel, 2);
            this.medicalPanel.revalidate();
            this.medicalPanel.repaint();
        }

        if(event.getSource() == this.currentLocation){
            this.existingLocationJPanel = existingLocationPanel();
            this.medicalPanel.remove(2);
            this.medicalPanel.add(this.existingLocationJPanel, 2);
            this.medicalPanel.revalidate();
            this.medicalPanel.repaint();
        }

        /* Family Relations Related Info */
        if(event.getSource() == this.submitRelations && this.newVictim.isSelected()){
            DisasterVictim newRelation;
            boolean validInfo = true;
            String fName = this.fnInputFR.getText();
            String lName = this.lnInputFR.getText();
            Integer age = (Integer) this.ageSpinnerFR.getValue();
            String dateOfBirth = this.dobInputFR.getText();
            String gender = this.genderInputFR.getText();
            String comments = this.commentsInputFR.getText();
            //Get current date for entry date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);

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
            //Retrieve all dietary restrictions
            ArrayList<DietaryRestriction> selectedOptions = new ArrayList<>();
            int [] selectedIndices = this.listFR.getSelectedIndices();
            for(int index : selectedIndices){
                selectedOptions.add(this.listModelFR.getElementAt(index));
            }
            for(DietaryRestriction restriction : selectedOptions){
                newRelation.addDietaryRestriction(restriction);
            }
            if(validInfo){
                this.dbConnect.addDisasterVictim(newRelation, (int)this.locationIDSpinnerFR.getValue(), new ArrayList<>());
                JOptionPane.showMessageDialog(this, "FamilyRelation created successfully!");
                this.relations.add(newRelation);
                this.relationships.add(this.relationshipToInput.getText());
                this.cardLayout.show(this.cardPanel, "main");
            }
        }

        if(event.getSource() == this.submitRelations && this.currentVictim.isSelected()){
            DisasterVictim newRelation;
            int selectedID = this.victimList.getSelectedIndex();
            if(selectedID == -1){
                JOptionPane.showMessageDialog(this, "Please choose a victim");
            }
            else{
                String fullName = this.victimList.getSelectedValue();
                String [] nameParts = fullName.split(" ");
                ArrayList<String> names = new ArrayList<>(Arrays.asList(nameParts));
                ResultSet victimInfo = this.dbConnect.retrieveDisasterVictim(names.get(0), names.get(1));
                try{
                    if(victimInfo.next()){
                        int age = victimInfo.getInt("age");
                        if(!victimInfo.wasNull()){
                            //Just using a temporary date, won't be using for searching later on so it doesn't matter here
                            newRelation = new DisasterVictim(names.get(0), "2024-04-03", victimInfo.getInt("age"));
                            newRelation.setLastName(victimInfo.getString("lname"));
                        }
                        else{
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String dateString = sdf.format(victimInfo.getDate("dob"));
                            newRelation = new DisasterVictim(names.get(0), "2024-04-03", dateString);
                            newRelation.setLastName(victimInfo.getString("lname"));
                        }
                        this.relations.add(newRelation);
                        this.relationships.add(this.relationshipToInput.getText());
                        JOptionPane.showMessageDialog(this, "FamilyRelation created successfully!");
                        this.cardLayout.show(this.cardPanel, "main");
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }

        if (event.getSource() == this.newVictim){
            this.familyRelationsPanel.remove(this.centerPanel);
            this.centerPanel = this.newPersonPanel();
            this.familyRelationsPanel.add(this.centerPanel, BorderLayout.CENTER);
            this.familyRelationsPanel.revalidate();
            this.familyRelationsPanel.repaint();
        }

        if (event.getSource() == this.currentVictim){
            this.familyRelationsPanel.remove(this.centerPanel);
            this.centerPanel = this.existingPerson();
            this.familyRelationsPanel.add(this.centerPanel, BorderLayout.CENTER);
            this.familyRelationsPanel.revalidate();
            this.familyRelationsPanel.repaint();
        }
    }
    
}
