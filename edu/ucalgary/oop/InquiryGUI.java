package edu.ucalgary.oop;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class InquiryGUI extends JFrame implements ActionListener{
    private Location inquiryLocation = new Location("Default", "Default");
    private DisasterVictim inquiryVictim = new DisasterVictim("", "2024-09-09", 0);
    private boolean centralWorkerFlag;

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private DBAccess dbConnection;

    //Components of the login page

    private JScrollPane locationScrollPane;
    private DefaultListModel<String> locationModel = new DefaultListModel<>();
    private JList<String> locationList = new JList<>(locationModel);

    //Components of the inquiry page
    private boolean victimSelected;
    private JPanel inquiryPage;
    private JLabel title;
    private JLabel dateOfInquiryLabel;
    private JLabel infoProvidedLabel;
    private JLabel inquirerFirstNameLabel;
    private JLabel inquirerLastNameLabel;
    private JLabel inquirerPhoneLabel;
    private JLabel locationNameLabel;
    private JLabel locationAddressLabel;
    private JTextField dateOfInquiryInput;
    private JTextField infoProvidedInput;
    private JTextField inquirerFirstNameInput;
    private JTextField inquirerLastNameInput;
    private JTextField inquirerPhoneInput;
    private JTextField locationNameInput;
    private JTextField locationAddressInput;

    private JButton searchVictimsButton;
    private JButton createNewPersonButton;
    private JButton submitInquiry;

    //Components of the searchVictim Page
    private JLabel firstNameSearchLabel;
    private JLabel lastNameSearchLabel;
    private JLabel resultsLabel;
    private JTextField firstNameSearchInput;
    private JTextField lastNameSearchInput;
    private JScrollPane results;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> list = new JList<>(listModel);

    private JButton searchButton;
    private JButton backHomeSVButton;
    private JButton chooseVictimButton;

    public Location getInquiryLocation(){
        return this.inquiryLocation;
    }

    public void setInquiryLocation(Location location){
        this.inquiryLocation = location;
    }

    //Constructor for central workers
    public InquiryGUI(){
        super("Inquiry Logging GUI");
        //Establish connection to database to search for victims
        this.dbConnection = new DBAccess();
        this.victimSelected = false;
        this.centralWorkerFlag = true;

        setupGUI(true);
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //Constructor for location workers
    public InquiryGUI(Location workerLocation){
        super("Inquiry Logging GUI");
        //Establish connection to database to search for victims
        this.dbConnection = new DBAccess();
        this.victimSelected = false;
        this.centralWorkerFlag = false;
        this.setInquiryLocation(workerLocation);

        setupGUI(false);
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void setupGUI(boolean workerFlag){
        //Creates the layout to switch between pages
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);

        //Creating the dynamic pages
        this.inquiryPage = this.setupInquiryPage(workerFlag);

        //Add all pages to the card panel:
        this.cardPanel.add(this.inquiryPage, "main");
        this.cardPanel.add(this.setupSearchVictimPage(), "search");

        //Add cardPanel to the main panel
        getContentPane().add(this.cardPanel);
    }

    private JPanel locationSelector(boolean inquiryPageFlag){
        //Location setup
        JPanel existingLocationPanel = new JPanel();
        existingLocationPanel.setLayout(new BoxLayout(existingLocationPanel, BoxLayout.Y_AXIS));
        this.locationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.locationScrollPane = new JScrollPane(this.locationList);
        ResultSet locations = this.dbConnection.retrieveAllLocations();
        try{
            while(locations.next()){
                String locationName = locations.getString("name");
                this.locationModel.addElement(locationName);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        if(!inquiryPageFlag){
            existingLocationPanel.add(new JLabel("Please select your location:(if location not listed, please contact supervisor)"));
        }
        existingLocationPanel.add(this.locationScrollPane);
        return existingLocationPanel;
    }

    private JPanel setupInquiryPage(boolean workerFlag){
        //Labels and inputs
        this.title = new JLabel("Welcome to the Inquiry Logging Page");

        //Buttons
        this.searchVictimsButton = new JButton("Search For Victims");
        this.createNewPersonButton = new JButton("Create New Victim");
        this.submitInquiry = new JButton("Submit");

        //Panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel(new FlowLayout());
        JPanel contentPanel = this.setupWorkerInput(workerFlag);
        JPanel buttonPanel = new JPanel(new GridLayout(3,1));

        //Add action listeners to the buttons
        this.createNewPersonButton.addActionListener(this);
        this.searchVictimsButton.addActionListener(this);
        this.submitInquiry.addActionListener(this);

        //Add to Panels
        headerPanel.add(this.title);
        buttonPanel.add(this.searchVictimsButton);
        buttonPanel.add(this.createNewPersonButton);
        buttonPanel.add(this.submitInquiry);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel setupWorkerInput(boolean workerflag){
        this.dateOfInquiryLabel = new JLabel("Please enter the date of inquiry");
        this.infoProvidedLabel = new JLabel("Please enter any additional info provided");
        this.inquirerFirstNameLabel = new JLabel("Inquirer First Name: ");
        this.inquirerLastNameLabel = new JLabel("Inquirer Last Name: ");
        this.inquirerPhoneLabel = new JLabel("Inquirer Phone Number: ");
        this.dateOfInquiryInput = new JTextField("e.g 2004-09-09", 15);
        this.infoProvidedInput = new JTextField("e.g Volunteer opportunities?", 15);
        this.inquirerFirstNameInput = new JTextField("e.g Logan", 15);
        this.inquirerLastNameInput = new JTextField("e.g Washington", 15);
        this.inquirerPhoneInput = new JTextField("e.g 403-111-1121", 15);
        
        JPanel labelPanel = new JPanel(new GridLayout(6, 1));
        JPanel inputPanel = new JPanel(new GridLayout(6, 1));

        labelPanel.add(this.dateOfInquiryLabel);
        labelPanel.add(this.infoProvidedLabel);
        labelPanel.add(this.inquirerFirstNameLabel);
        labelPanel.add(this.inquirerLastNameLabel);
        labelPanel.add(this.inquirerPhoneLabel);
        inputPanel.add(this.dateOfInquiryInput);
        inputPanel.add(this.infoProvidedInput);
        inputPanel.add(this.inquirerFirstNameInput);
        inputPanel.add(this.inquirerLastNameInput);
        inputPanel.add(this.inquirerPhoneInput);
        if (workerflag){
            labelPanel.add(new JLabel("Please choose the location of the inquiry"));
            inputPanel.add(this.locationSelector(true));
        }
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(labelPanel);
        mainPanel.add(inputPanel);

        return mainPanel;
    }

    private JPanel setupSearchVictimPage(){
        //Set all labels
        this.firstNameSearchLabel = new JLabel("First Name: ");
        this.lastNameSearchLabel = new JLabel("Last Name:(Optional) ");
        this.resultsLabel = new JLabel("Search Results");
        this.firstNameSearchInput = new JTextField("e.g Dorothy");
        this.lastNameSearchInput = new JTextField("e.g Gale");
        this.listModel = new DefaultListModel<>();
        this.list = new JList<>(this.listModel);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Unfortunately, users will have to hold the control button to multi-select
        this.results = new JScrollPane(list);
        this.searchButton = new JButton("Search");
        this.backHomeSVButton = new JButton("Home");
        //Not visible initially, but will become visible once a victim is chosen
        this.chooseVictimButton = new JButton("Select current victim");

        //Add action listeners
        this.searchButton.addActionListener(this);
        this.backHomeSVButton.addActionListener(this);
        this.chooseVictimButton.addActionListener(this);

        //Create the page
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridLayout(3, 1));
        JPanel inputPanel = new JPanel(new GridLayout(3, 1));
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1));
        labelPanel.add(this.firstNameSearchLabel);
        labelPanel.add(this.lastNameSearchLabel);
        labelPanel.add(this.resultsLabel);
        inputPanel.add(this.firstNameSearchInput);
        inputPanel.add(this.lastNameSearchInput);
        inputPanel.add(this.results);
        buttonsPanel.add(this.searchButton);
        buttonsPanel.add(this.backHomeSVButton);
        //Will add the choose a victim button when an option is selected
        this.list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e){
                buttonsPanel.add(chooseVictimButton);
                buttonsPanel.revalidate();
                buttonsPanel.repaint();
            }
        });
        mainPanel.add(labelPanel, BorderLayout.WEST);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    public void actionPerformed(ActionEvent e){
        /*Inquiry Page Actions */
        if(e.getSource() == this.searchVictimsButton){
            this.cardLayout.show(this.cardPanel, "search");
        }
        if(e.getSource() == this.createNewPersonButton){
            if(centralWorkerFlag){
                DisasterVictimLogging victimGUI = new DisasterVictimLogging();
                victimGUI.setVisible(true);
                victimGUI.setInquiryOpen(true);
            }
            else{
                ResultSet location = this.dbConnection.retrieveLocation(this.getInquiryLocation().getName());
                Integer locationID = -1;
                try{
                    if(location.next()){
                        locationID = location.getInt("location_id");
                        DisasterVictimLogging victimGUI = new DisasterVictimLogging(locationID);
                        victimGUI.setVisible(true);
                        victimGUI.setInquiryOpen(true);
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
        if(e.getSource() == this.submitInquiry){
            boolean validData = true;
            String dateOfEntry = this.dateOfInquiryInput.getText();
            String infoProvided = this.infoProvidedInput.getText();
            String inquirerFname = this.inquirerFirstNameInput.getText();
            String inquirerLname = this.inquirerLastNameInput.getText();
            String inquirerPhone = this.inquirerPhoneInput.getText();
            if(centralWorkerFlag){
                int selectedIndex = this.locationList.getSelectedIndex();
                if(selectedIndex == -1){
                    JOptionPane.showMessageDialog(this, "Please choose a location before submitting");
                    validData = false;
                }
                else{
                    String locationName = this.locationList.getSelectedValue();
                    try{
                        ResultSet result = this.dbConnection.retrieveLocation(locationName);
                        if (result.next()){
                            this.inquiryLocation = new Location(locationName, result.getString("address"));
                        }
                        else{
                            JOptionPane.showMessageDialog(this, "There was a problem retrieving your location");
                        }
                    }
                    catch(SQLException ex){
                        ex.printStackTrace();
                    }
                }
            }
            if(dateOfEntry.trim().equals("e.g 2004-09-09") || !DisasterVictim.isValidDateFormat(dateOfEntry)){
                JOptionPane.showMessageDialog(this, "Invalid Date");
                validData = false;
            }
            if(infoProvided.trim() == "e.g Volunteer opportunities?" || infoProvided.trim().equals("")){
                JOptionPane.showMessageDialog(this, "Invalid Info");
                validData = false;
            }
            if(inquirerFname.trim().equals("e.g Logan") || inquirerFname.trim().equals("")){
                JOptionPane.showMessageDialog(this, "Invalid Inquirer First Name");
                validData = false;
            }
            if(inquirerLname.trim().equals("e.g Washington") || inquirerLname.trim().equals("")){
                JOptionPane.showMessageDialog(this, "Invalid Inquirer Last Name");
                validData = false;
            }
            if(inquirerPhone.trim().equals("e.g 403-111-1121") || inquirerPhone.trim().equals("")){
                JOptionPane.showMessageDialog(this, "Invalid Inquirer Phone Number");
                validData = false;
            }

            if(validData){
                int socialID = -1;
                int locationID = -1;
                int inquirerID = -1;
                if(this.victimSelected){
                    //Retrival of victim if necessary
                    try{
                        ResultSet victimSet = this.dbConnection.retrieveDisasterVictim(this.inquiryVictim.getFirstName(), this.inquiryVictim.getLastName());
                        if(victimSet.next()){
                            socialID = victimSet.getInt("social_id");
                        }
                    }
                    catch(SQLException ex){
                        ex.printStackTrace();
                    }
                }
                //Retrieve all data other than the victim
                try{
                    ResultSet locationSet = this.dbConnection.retrieveLocation(inquiryLocation.getName());
                    ResultSet inquirerSet = this.dbConnection.retrieveInquirer(inquirerFname, inquirerLname);
                    if(locationSet.next()){
                        locationID = locationSet.getInt("location_id");
                    }
                    if(inquirerSet.next()){
                        inquirerID = inquirerSet.getInt("id");
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Inquirer does not exist, creating now");
                        this.dbConnection.addInquirer(inquirerFname, inquirerLname, inquirerPhone);
                        inquirerSet = this.dbConnection.retrieveInquirer(inquirerFname, inquirerLname);
                        //Will get it now since it has just been added
                        if(inquirerSet.next()){
                            inquirerID = inquirerSet.getInt("id");
                        }
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
                Inquiry newInquiry = new Inquiry(this.inquiryVictim, dateOfEntry, infoProvided);
                this.dbConnection.addInquiry(socialID, locationID, inquirerID, newInquiry);
                JOptionPane.showMessageDialog(this, "Successfully added inquiry");
                System.exit(0);
            }
        }

        /*Search Page Actions */
        if(e.getSource() == this.backHomeSVButton){
            this.cardLayout.show(this.cardPanel, "main");
        }
        if (e.getSource() == this.searchButton){
            this.listModel.clear();
            String fName = this.firstNameSearchInput.getText();
            String lName = this.lastNameSearchInput.getText();
            if (fName.trim().equals("e.g Dorothy")){
                JOptionPane.showMessageDialog(this, "Need a first name to search");
            }
            else if(lName.trim().equals("e.g Gale")){
                ResultSet results = this.dbConnection.searchDisasterVictim(fName.trim());
                try{
                    while(results.next()){
                        String name = results.getString("fname") + " " + results.getString("lname");
                        this.listModel.addElement(name);
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            else{
                ResultSet results = this.dbConnection.searchDisasterVictim(fName.trim(), lName.trim());
                try{
                    while(results.next()){
                        String name = results.getString("fname") +  " " + results.getString("lname");
                        this.listModel.addElement(name);
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
        if(e.getSource() == this.chooseVictimButton){
            String name = this.list.getSelectedValue();
            String [] nameParts = name.split(" ");
            ArrayList<String> names = new ArrayList<>(Arrays.asList(nameParts));
            ResultSet victimInfo = this.dbConnection.retrieveDisasterVictim(names.get(0), names.get(1));
            try{
                if(victimInfo.next()){
                    if(!victimInfo.wasNull()){
                        //Just using a temporary date, won't be using for searching later on so it doesn't matter here
                        this.inquiryVictim = new DisasterVictim(names.get(0), "2024-04-03", victimInfo.getInt("age"));
                        this.inquiryVictim.setLastName(victimInfo.getString("lname"));
                    }
                    else{
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = sdf.format(victimInfo.getDate("dob"));
                        this.inquiryVictim = new DisasterVictim(names.get(0), "2024-04-03", dateString);
                        this.inquiryVictim.setLastName(victimInfo.getString("lname"));
                    }
                    JOptionPane.showMessageDialog(this, "DisasterVicitm Chosen");
                    this.victimSelected = true;
                    this.cardLayout.show(this.cardPanel, "main");
                }
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    
}
