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
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private DBAccess dbConnection;

    //Components of the login page
    private JPanel loginPage;
    private JLabel loginTitle;
    private JLabel workerTypeLabel;
    private JPanel workerTypeInput;
    private JPanel currentLocations;
    private JRadioButtonMenuItem centralWorker;
    private JRadioButtonMenuItem locationWorker;
    private JScrollPane locationScrollPane;
    private DefaultListModel<String> locationModel = new DefaultListModel<>();
    private JList<String> locationList = new JList<>(locationModel);
    private JButton loginButton;

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

    public InquiryGUI(){
        super("Inquiry Logging GUI");
        //Establish connection to database to search for victims
        this.dbConnection = new DBAccess();
        victimSelected = false;

        setupGUI();
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setupGUI(){
        //Creates the layout to switch between pages
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //Creating the dynamic pages
        loginPage = this.setupLogin(false);
        inquiryPage = this.setupInquiryPage();

        //Add all pages to the card panel:
        cardPanel.add(loginPage, "main");
        cardPanel.add(inquiryPage, "inquiry");
        cardPanel.add(this.setupSearchVictimPage(), "search");

        //Add cardPanel to the main panel
        getContentPane().add(cardPanel);
    }

    private JPanel setupLogin(boolean loginFlag){
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
        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        currentLocations = this.locationSelector(false);
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
        if(loginFlag){
            mainPanel.add(currentLocations);
        }
        else{
            mainPanel.add(new JPanel());
        }
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel locationSelector(boolean inquiryPageFlag){
        //Location setup
        JPanel existingLocationPanel = new JPanel();
        existingLocationPanel.setLayout(new BoxLayout(existingLocationPanel, BoxLayout.Y_AXIS));
        locationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        locationScrollPane = new JScrollPane(locationList);
        ResultSet locations = this.dbConnection.retrieveAllLocations();
        try{
            while(locations.next()){
                String locationName = locations.getString("name");
                locationModel.addElement(locationName);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        if(!inquiryPageFlag){
            existingLocationPanel.add(new JLabel("Please select your location:(if location not listed, please contact supervisor)"));
        }
        existingLocationPanel.add(locationScrollPane);
        return existingLocationPanel;
    }

    private JPanel setupInquiryPage(){
        //Labels and inputs
        title = new JLabel("Welcome to the Inquiry Logging Page");

        //Buttons
        searchVictimsButton = new JButton("Search For Victims");
        createNewPersonButton = new JButton("Create New Victim");
        submitInquiry = new JButton("Submit");

        //Panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel(new FlowLayout());
        JPanel contentPanel = this.setupWorkerInput(false);
        JPanel buttonPanel = new JPanel(new GridLayout(3,1));

        //Add action listeners to the buttons
        createNewPersonButton.addActionListener(this);
        searchVictimsButton.addActionListener(this);
        submitInquiry.addActionListener(this);

        //Add to Panels
        headerPanel.add(title);
        buttonPanel.add(searchVictimsButton);
        buttonPanel.add(createNewPersonButton);
        buttonPanel.add(submitInquiry);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel setupWorkerInput(boolean workerflag){
        dateOfInquiryLabel = new JLabel("Please enter the date of inquiry");
        infoProvidedLabel = new JLabel("Please enter any additional info provided");
        inquirerFirstNameLabel = new JLabel("Inquirer First Name: ");
        inquirerLastNameLabel = new JLabel("Inquirer Last Name: ");
        inquirerPhoneLabel = new JLabel("Inquirer Phone Number: ");
        dateOfInquiryInput = new JTextField("e.g 2004-09-09", 15);
        infoProvidedInput = new JTextField("e.g Volunteer opportunities?", 15);
        inquirerFirstNameInput = new JTextField("e.g Logan", 15);
        inquirerLastNameInput = new JTextField("e.g Washington", 15);
        inquirerPhoneInput = new JTextField("e.g 403-111-1121", 15);
        
        JPanel labelPanel = new JPanel(new GridLayout(6, 1));
        JPanel inputPanel = new JPanel(new GridLayout(6, 1));

        labelPanel.add(dateOfInquiryLabel);
        labelPanel.add(infoProvidedLabel);
        labelPanel.add(inquirerFirstNameLabel);
        labelPanel.add(inquirerLastNameLabel);
        labelPanel.add(inquirerPhoneLabel);
        inputPanel.add(dateOfInquiryInput);
        inputPanel.add(infoProvidedInput);
        inputPanel.add(inquirerFirstNameInput);
        inputPanel.add(inquirerLastNameInput);
        inputPanel.add(inquirerPhoneInput);
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
        firstNameSearchLabel = new JLabel("First Name: ");
        lastNameSearchLabel = new JLabel("Last Name:(Optional) ");
        resultsLabel = new JLabel("Search Results");
        firstNameSearchInput = new JTextField("e.g Dorothy");
        lastNameSearchInput = new JTextField("e.g Gale");
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Unfortunately, users will have to hold the control button to multi-select
        results = new JScrollPane(list);
        searchButton = new JButton("Search");
        backHomeSVButton = new JButton("Home");
        //Not visible initially, but will become visible once a victim is chosen
        chooseVictimButton = new JButton("Select current victim");

        //Add action listeners
        searchButton.addActionListener(this);
        backHomeSVButton.addActionListener(this);
        chooseVictimButton.addActionListener(this);

        //Create the page
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridLayout(3, 1));
        JPanel inputPanel = new JPanel(new GridLayout(3, 1));
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1));
        labelPanel.add(firstNameSearchLabel);
        labelPanel.add(lastNameSearchLabel);
        labelPanel.add(resultsLabel);
        inputPanel.add(firstNameSearchInput);
        inputPanel.add(lastNameSearchInput);
        inputPanel.add(results);
        buttonsPanel.add(searchButton);
        buttonsPanel.add(backHomeSVButton);
        //Will add the choose a victim button when an option is selected
        list.addListSelectionListener(new ListSelectionListener() {
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
        //Variables to be saved for submission
        Location inquiry_location = new Location("Default", "Default");
        DisasterVictim inquiry_victim = new DisasterVictim("", "2024-09-09", 0);
        /*Main Page Actions */
        if(e.getSource() == loginButton && centralWorker.isSelected()){
            Component toBeRemoved = inquiryPage.getComponent(1);
            JPanel newComponent = this.setupWorkerInput(true);
            inquiryPage.remove(toBeRemoved);
            inquiryPage.add(newComponent, 1);
            inquiryPage.revalidate();
            inquiryPage.repaint();
            cardLayout.show(cardPanel, "inquiry");
        }
        if(e.getSource() == loginButton && locationWorker.isSelected()){
            int selectedIndex = locationList.getSelectedIndex();
            if(selectedIndex != -1){
                String locationName = locationList.getSelectedValue();
                try{
                    ResultSet result = this.dbConnection.retrieveLocation(locationName);
                    if (result.next()){
                        inquiry_location = new Location(locationName, result.getString("address"));
                        cardLayout.show(cardPanel, "inquiry");
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "There was a problem retrieving your location");
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Please select a location before proceeding");
            }
        }
        if(e.getSource() == centralWorker){
            //Set back to no location tab
            loginPage.remove(2);
            loginPage.add(new JPanel(), 2);
            loginPage.revalidate();
            loginPage.repaint();
        }
        if(e.getSource() == locationWorker){
            //Set back to no location tab
            JPanel locations = this.locationSelector(false);
            loginPage.remove(2);
            loginPage.add(locations, 2);
            loginPage.revalidate();
            loginPage.repaint();
        }

        /*Inquiry Page Actions */
        if(e.getSource() == searchVictimsButton){
            cardLayout.show(cardPanel, "search");
        }
        if(e.getSource() == createNewPersonButton){
            DisasterVictimLogging vicitmGUI = new DisasterVictimLogging();
            vicitmGUI.setVisible(true);
            vicitmGUI.setInquiryOpen(true);
        }
        if(e.getSource() == submitInquiry){
            boolean validData = true;
            String dateOfEntry = dateOfInquiryInput.getText();
            String infoProvided = infoProvidedInput.getText();
            String inquirerFname = inquirerFirstNameInput.getText();
            String inquirerLname = inquirerLastNameInput.getText();
            String inquirerPhone = inquirerPhoneInput.getText();
            if(centralWorker.isSelected()){
                int selectedIndex = locationList.getSelectedIndex();
                if(selectedIndex == -1){
                    JOptionPane.showMessageDialog(this, "Please choose a location before submitting");
                    validData = false;
                }
                else{
                    String locationName = locationList.getSelectedValue();
                    try{
                        ResultSet result = this.dbConnection.retrieveLocation(locationName);
                        if (result.next()){
                            inquiry_location = new Location(locationName, result.getString("address"));
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
                if(victimSelected){
                    int socialID = 0;
                    int locationID = 0;
                    //Retrival of all foreign keys from the database
                    try{
                        ResultSet victimSet = this.dbConnection.retrieveDisasterVictim(inquiry_victim.getFirstName(), inquiry_victim.getLastName());
                        ResultSet locationSet = this.dbConnection.retrieveLocation(inquiry_location.getName());
                        if(victimSet.next()){
                            socialID = victimSet.getInt("social_id");
                        }
                        if(locationSet.next()){
                            locationID = locationSet.getInt("location_id");
                        }
                    }
                    catch(SQLException ex){
                        ex.printStackTrace();
                    }
                }
            }
        }

        /*Search Page Actions */
        if(e.getSource() == backHomeSVButton){
            cardLayout.show(cardPanel, "inquiry");
        }
        if (e.getSource() == searchButton){
            listModel.clear();
            String fName = firstNameSearchInput.getText();
            String lName = lastNameSearchInput.getText();
            if (fName.trim().equals("e.g Dorothy")){
                JOptionPane.showMessageDialog(this, "Need a first name to search");
            }
            else if(lName.trim().equals("e.g Gale")){
                ResultSet results = dbConnection.searchDisasterVictim(fName.trim());
                try{
                    while(results.next()){
                        String name = results.getString("fname") + " " + results.getString("lname");
                        listModel.addElement(name);
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            else{
                ResultSet results = dbConnection.searchDisasterVictim(fName.trim(), lName.trim());
                try{
                    while(results.next()){
                        String name = results.getString("fname") +  " " + results.getString("lname");
                        listModel.addElement(name);
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
        if(e.getSource() == chooseVictimButton){
            String name = list.getSelectedValue();
            String [] nameParts = name.split(" ");
            ArrayList<String> names = new ArrayList<>(Arrays.asList(nameParts));
            ResultSet victimInfo = this.dbConnection.retrieveDisasterVictim(names.get(0), names.get(1));
            try{
                if(victimInfo.next()){
                    int age = victimInfo.getInt("age");
                    if(!victimInfo.wasNull()){
                        //Just using a temporary date, won't be using for searching later on so it doesn't matter here
                        inquiry_victim = new DisasterVictim(names.get(0), "2024-04-03", victimInfo.getInt("age"));
                        inquiry_victim.setLastName(victimInfo.getString("lname"));
                    }
                    else{
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = sdf.format(victimInfo.getDate("dob"));
                        inquiry_victim = new DisasterVictim(names.get(0), "2024-04-03", dateString);
                        inquiry_victim.setLastName(victimInfo.getString("lname"));
                    }
                    JOptionPane.showMessageDialog(this, "DisasterVicitm Chosen");
                    this.victimSelected = true;
                    cardLayout.show(cardPanel, "inquiry");
                }
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new InquiryGUI().setVisible(true);
        });
    }
    
}
