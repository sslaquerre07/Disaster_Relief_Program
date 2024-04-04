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


    public DisasterVictimLogging(){
        super("Disaster Victim Logging GUI");
        this.dbConnect = new DBAccess();

        setupGUI();
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void setupGUI(){
        //Creates the layout to switch between pages
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        mainPanel = this.setupMain();
        medicalPanel = this.medicalRecordPage();
        familyRelationsPanel = this.familyRelationsPage();

        //Add all pages to the card panel:
        cardPanel.add(mainPanel, "main");
        cardPanel.add(medicalPanel, "medical");
        cardPanel.add(familyRelationsPanel, "relation");

        //Add cardPanel to the main panel
        getContentPane().add(cardPanel);
    }

    /*Main page related functions */
    private JPanel setupMain(){
        title = new JLabel("Welcome to the Disaster Victim Logging Page");

        //Set all labels
        fnLabel = new JLabel("First Name: ");
        lnLabel = new JLabel("Last Name:(Optional) ");
        ageLabel = new JLabel("Approx age(Choose either this or date of birth): ");
        dobLabel = new JLabel("Date of Birth: ");
        locationIDLabel = new JLabel("Please enter the location ID");
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
        SpinnerModel locationID = new SpinnerNumberModel(18, 0, 150, 1);
        locationIDSpinner = new JSpinner(locationID);
        JSpinner.NumberEditor editorL = new JSpinner.NumberEditor(locationIDSpinner, "#");
        locationIDSpinner.setEditor(editorL);
        genderInput = new JTextField("e.g male", 15);
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
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
        JPanel titlePanel = new JPanel(new GridLayout(8,1));
        JPanel inputPanel = new JPanel(new GridLayout(8,1));
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());

        //Adding all of the components
        headerPanel.add(title);
        titlePanel.add(fnLabel);
        titlePanel.add(lnLabel);
        titlePanel.add(ageLabel);
        titlePanel.add(dobLabel);
        titlePanel.add(locationIDLabel);
        titlePanel.add(genderLabel);
        titlePanel.add(dietaryLabel);
        titlePanel.add(commentsLabel);
        inputPanel.add(fnInput);
        inputPanel.add(lnInput);
        inputPanel.add(ageSpinner);
        inputPanel.add(dobInput);
        inputPanel.add(locationIDSpinner);
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

    /*Medical Record page related functions */
    private JPanel medicalRecordPage(){
        //Set all inputs
        locationSelection = new JPanel(new GridLayout(1, 2));
        ButtonGroup group = new ButtonGroup();
        currentLocation = new JRadioButtonMenuItem("Current Location");
        newLocation = new JRadioButtonMenuItem("Create a new location");
        group.add(currentLocation);
        group.add(newLocation);
        locationSelection.add(currentLocation);
        locationSelection.add(newLocation);
        newLocationJPanel = this.newLocationPanel();

        //Create all buttons
        backHomeButtonMR = new JButton("Home");
        submitMRInfoButton = new JButton("Submit");

        //Add action listeners
        backHomeButtonMR.addActionListener(this);
        submitMRInfoButton.addActionListener(this);
        currentLocation.addActionListener(this);
        newLocation.addActionListener(this);

        //Create all panels
        JPanel headerPanel = new JPanel(new FlowLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));

        //Create header panel
        headerPanel.add(title);

        //Create buttonsPanel
        buttonPanel.add(backHomeButtonMR);
        buttonPanel.add(submitMRInfoButton);

        //Create final panel
        JPanel medicalRecordPanel = new JPanel(new GridLayout(4, 1));
        medicalRecordPanel.add(headerPanel);
        medicalRecordPanel.add(locationSelection);
        medicalRecordPanel.add(new JPanel());
        this.newLocationPanel().setVisible(false);
        medicalRecordPanel.add(buttonPanel); 

        return medicalRecordPanel;
    }

    private JPanel newLocationPanel(){
        nameLabel = new JLabel("Location Name");
        addressLabel = new JLabel("Location Address");
        treatmentLabel = new JLabel("Enter treatment details");
        dateOfTreatmentLabel = new JLabel("Enter date of treatment");

        nameInput = new JTextField("e.g Telus Spark Centre", 15);
        addressInput = new JTextField("e.g 123 Sesame Street", 15);
        treatmentInput = new JTextField("e.g Wrist Surgery");
        dateOfTreatmentInput = new JTextField("e.g 2022-09-09", 15);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridLayout(4, 1));
        JPanel contentPanel = new JPanel(new GridLayout(4, 1));

        labelPanel.add(nameLabel);
        labelPanel.add(addressLabel);
        labelPanel.add(dateOfTreatmentLabel);
        labelPanel.add(treatmentLabel);
        contentPanel.add(nameInput);
        contentPanel.add(addressInput);
        contentPanel.add(dateOfTreatmentInput);
        contentPanel.add(treatmentInput);
        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel existingLocationPanel(){
        //Treatment details and date
        treatmentLabel = new JLabel("Enter treatment details");
        dateOfTreatmentLabel = new JLabel("Enter date of treatment");
        treatmentInput = new JTextField("e.g Wrist Surgery");
        dateOfTreatmentInput = new JTextField("e.g 2022-09-09", 15);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.add(dateOfTreatmentLabel);
        labelPanel.add(treatmentLabel);
        contentPanel.add(dateOfTreatmentInput);
        contentPanel.add(treatmentInput);
        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(contentPanel, BorderLayout.CENTER);

        //Location setup
        existingLocationJPanel = new JPanel(new GridLayout(2,1));
        listModelMR = new DefaultListModel<>();
        listMR = new JList<>(listModelMR);
        listMR.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultsMR = new JScrollPane(listMR);
        ResultSet locations = this.dbConnect.retrieveAllLocations();
        try{
            while(locations.next()){
                String locationName = locations.getString("name");
                listModelMR.addElement(locationName);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        existingLocationJPanel.add(resultsMR);
        existingLocationJPanel.add(panel);
        return existingLocationJPanel;
    }

    /*Family Relation page related functions */
    private JPanel familyRelationsPage(){
        //Add all inputs and titles
        title = new JLabel("Family Relations Page");
        victimSelection = new JPanel(new GridLayout(1, 2));
        centerPanel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        currentVictim = new JRadioButtonMenuItem("Choose from existing Victims");
        newVictim = new JRadioButtonMenuItem("Create a new vicitm");
        group.add(currentVictim);
        group.add(newVictim);
        victimSelection.add(currentVictim);
        victimSelection.add(newVictim);

        //Add Button
        submitRelations = new JButton("Submit");
        backHomeButtonFR = new JButton("Home");

        //Add Button Listeners
        currentVictim.addActionListener(this);
        newVictim.addActionListener(this);
        submitRelations.addActionListener(this);
        backHomeButtonFR.addActionListener(this);

        //Panels for the home page
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());

        //Adding all of the components
        headerPanel.add(title);
        headerPanel.add(victimSelection);
        buttonPanel.add(submitRelations);
        buttonPanel.add(backHomeButtonFR);

        //Main panel that will hold everything:
        JPanel relationPanel = new JPanel(new BorderLayout());
        relationPanel.add(headerPanel, BorderLayout.NORTH);
        relationPanel.add(centerPanel, BorderLayout.CENTER);
        relationPanel.add(buttonPanel, BorderLayout.SOUTH);

        return relationPanel;
    }

    private JPanel newPersonPanel(){
        //Set all labels
        fnLabelFR = new JLabel("First Name: ");
        lnLabelFR = new JLabel("Last Name:(Optional) ");
        ageLabelFR = new JLabel("Approx age(Choose either this or date of birth): ");
        dobLabelFR = new JLabel("Date of Birth: ");
        genderLabelFR = new JLabel("Enter your gender:(Optional) ");
        dietaryLabelFR = new JLabel("<html>Please select all necessary dietary restrictions<br />Hold the CTRL key while selecting if you have multiple</html>");
        locationLabelFR = new JLabel("Choose location ID");
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
        listFR.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //Unfortunately, users will have to hold the control button to multi-select
        dietaryInputFR = new JScrollPane(listFR);
        listModelFR.addElement(DietaryRestriction.AVML);
        listModelFR.addElement(DietaryRestriction.DBML);
        listModelFR.addElement(DietaryRestriction.GFML);
        listModelFR.addElement(DietaryRestriction.KSML);
        listModelFR.addElement(DietaryRestriction.LSML);
        listModelFR.addElement(DietaryRestriction.MOML);
        listModelFR.addElement(DietaryRestriction.PFML);
        listModelFR.addElement(DietaryRestriction.VGML);
        listModelFR.addElement(DietaryRestriction.VJML);
        SpinnerModel locationIDFR = new SpinnerNumberModel(1, 0, 150, 1);
        locationIDSpinnerFR = new JSpinner(locationIDFR);
        JSpinner.NumberEditor editorFR = new JSpinner.NumberEditor(locationIDSpinnerFR, "#");
        locationIDSpinner.setEditor(editorFR);
        commentsInputFR = new JTextField("e.g Lost in flood", 15);
        relationshipToInput = new JTextField("e.g sibling", 15);

        JPanel titlePanel = new JPanel(new GridLayout(9,1));
        JPanel inputPanel = new JPanel(new GridLayout(9,1));

        titlePanel.add(fnLabelFR);
        titlePanel.add(lnLabelFR);
        titlePanel.add(ageLabelFR);
        titlePanel.add(dobLabelFR);
        titlePanel.add(genderLabelFR);
        titlePanel.add(dietaryLabelFR);
        titlePanel.add(locationLabelFR);
        titlePanel.add(commentsLabelFR);
        titlePanel.add(relationshipToLabel);
        inputPanel.add(fnInputFR);
        inputPanel.add(lnInputFR);
        inputPanel.add(ageSpinnerFR);
        inputPanel.add(dobInputFR);
        inputPanel.add(genderInputFR);
        inputPanel.add(dietaryInputFR);
        inputPanel.add(locationIDSpinnerFR);
        inputPanel.add(commentsInputFR);
        inputPanel.add(relationshipToInput);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titlePanel, BorderLayout.WEST);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel existingPerson(){
        JPanel existingPersonPanel = new JPanel();
        existingPersonPanel.setLayout(new BoxLayout(existingPersonPanel, BoxLayout.Y_AXIS));
        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        victimModel = new DefaultListModel<>();
        victimList = new JList<>(victimModel);
        victimList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        victimResults = new JScrollPane(victimList);
        relationshipToLabel = new JLabel("Relationship to the victim");
        relationshipToInput = new JTextField("e.g sibling", 15);
        ResultSet victims = this.dbConnect.retrieveAllDisasterVictims();
        try{
            while(victims.next()){
                String victimName = victims.getString("fname") + " " + victims.getString("lname");
                victimModel.addElement(victimName);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        existingPersonPanel.add(victimResults);
        contentPanel.add(relationshipToLabel);
        contentPanel.add(relationshipToInput);
        existingPersonPanel.add(contentPanel);
        return existingPersonPanel;
    }

    /*Handle all button presses and option selections */
    public void actionPerformed(ActionEvent event){
        /*Brief summary of what it's doing right now
         * There is no instruction for how to check a stored location
         * so we're just creating a new instance for every time you 
         * utilize the interface!!
         */
        
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
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);

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
            System.out.println(victim.getMedicalRecords().size());
            if(!this.dbConnect.validLocationID((int) locationIDSpinner.getValue())){
                JOptionPane.showMessageDialog(this, "Invalid Location ID");
                validInfo = false;
            }
            if(validInfo){
                this.dbConnect.addDisasterVictim(victim, (int) locationIDSpinner.getValue(), relations);
                JOptionPane.showMessageDialog(this, "DisasterVictim created successfully!");
                this.dbConnect.close();
                System.exit(0);
            }
        }

        /* Medical Records Related Info */
        if(event.getSource() == submitMRInfoButton && newLocation.isSelected()){
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
                this.dbConnect.addLocation(location);
                medicalRecords.add(new MedicalRecord(location, treatmentDetailString, dateOfTreatmentString));
                System.out.println(medicalRecords.size());
                JOptionPane.showMessageDialog(this, "Medical Record created and added successfully");
                cardLayout.show(cardPanel, "main");
            }
        }
        if (event.getSource() == submitMRInfoButton && currentLocation.isSelected()){
            boolean validData = true;
            String treatmentDetailString = treatmentInput.getText();
            String dateOfTreatmentString = dateOfTreatmentInput.getText();
            int selectedIndex = listMR.getSelectedIndex();

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
                String locationName = listMR.getModel().getElementAt(selectedIndex);
                ResultSet locationInfo = this.dbConnect.retrieveLocation(locationName);
                try{
                    if(locationInfo.next()){
                        medicalRecords.add(new MedicalRecord(new Location(locationName, locationInfo.getString("address")), treatmentDetailString, dateOfTreatmentString));
                        JOptionPane.showMessageDialog(this, "Medical Record created and added successfully");
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Something went wrong with creating your medical record");
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
                System.out.println(medicalRecords.size());
                cardLayout.show(cardPanel, "main");
            }
        }

        if(event.getSource() == newLocation){
            newLocationJPanel = newLocationPanel();
            medicalPanel.remove(2);
            medicalPanel.add(newLocationJPanel, 2);
            medicalPanel.revalidate();
            medicalPanel.repaint();
        }

        if(event.getSource() == currentLocation){
            existingLocationJPanel = existingLocationPanel();
            medicalPanel.remove(2);
            medicalPanel.add(existingLocationJPanel, 2);
            medicalPanel.revalidate();
            medicalPanel.repaint();
        }

        /* Family Relations Related Info */
        if(event.getSource() == submitRelations && newVictim.isSelected()){
            DisasterVictim newRelation;
            boolean validInfo = true;
            String fName = fnInputFR.getText();
            String lName = lnInputFR.getText();
            Integer age = (Integer) ageSpinnerFR.getValue();
            String dateOfBirth = dobInputFR.getText();
            String gender = genderInputFR.getText();
            String comments = commentsInputFR.getText();
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
            int [] selectedIndices = listFR.getSelectedIndices();
            for(int index : selectedIndices){
                selectedOptions.add(listModelFR.getElementAt(index));
            }
            for(DietaryRestriction restriction : selectedOptions){
                newRelation.addDietaryRestriction(restriction);
            }
            if(validInfo){
                this.dbConnect.addDisasterVictim(newRelation, (int)locationIDSpinnerFR.getValue(), new ArrayList<>());
                JOptionPane.showMessageDialog(this, "FamilyRelation created successfully!");
                relations.add(newRelation);
                relationships.add(relationshipToInput.getText());
                cardLayout.show(cardPanel, "main");
            }
        }

        if(event.getSource() == submitRelations && currentVictim.isSelected()){
            DisasterVictim newRelation;
            int selectedID = victimList.getSelectedIndex();
            if(selectedID == -1){
                JOptionPane.showMessageDialog(this, "Please choose a victim");
            }
            else{
                String fullName = victimList.getSelectedValue();
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
                        relations.add(newRelation);
                        relationships.add(relationshipToInput.getText());
                        JOptionPane.showMessageDialog(this, "FamilyRelation created successfully!");
                        cardLayout.show(cardPanel, "main");
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }

        if (event.getSource() == newVictim){
            familyRelationsPanel.remove(centerPanel);
            centerPanel = this.newPersonPanel();
            familyRelationsPanel.add(centerPanel, BorderLayout.CENTER);
            familyRelationsPanel.revalidate();
            familyRelationsPanel.repaint();
        }

        if (event.getSource() == currentVictim){
            familyRelationsPanel.remove(centerPanel);
            centerPanel = this.existingPerson();
            familyRelationsPanel.add(centerPanel, BorderLayout.CENTER);
            familyRelationsPanel.revalidate();
            familyRelationsPanel.repaint();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new DisasterVictimLogging().setVisible(true);
        });
    }
    
}
