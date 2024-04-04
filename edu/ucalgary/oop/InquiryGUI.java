package edu.ucalgary.oop;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private JButton loginButton;

    //Components of the inquiry page
    private JLabel title;
    private JLabel dateOfInquiryLabel;
    private JLabel infoProvidedLabel;
    private JLabel locationNameLabel;
    private JLabel locationAddressLabel;
    private JTextField dateOfInquiryInput;
    private JTextField infoProvidedInput;
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

        //Add all pages to the card panel:
        cardPanel.add(loginPage, "main");
        cardPanel.add(this.setupInquiryPage(), "inquiry");
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
        currentLocations = this.locationSelector();
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

    private JPanel locationSelector(){
        //Location setup
        JPanel existingLocationPanel = new JPanel();
        existingLocationPanel.setLayout(new BoxLayout(existingLocationPanel, BoxLayout.Y_AXIS));
        DefaultListModel<String> listModelInq = new DefaultListModel<>();
        JList<String> listInq = new JList<>(listModelInq);
        listInq.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane resultsInq = new JScrollPane(listInq);
        ResultSet locations = this.dbConnection.retrieveAllLocations();
        try{
            while(locations.next()){
                String locationName = locations.getString("name");
                listModelInq.addElement(locationName);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        existingLocationPanel.add(new JLabel("Please select your location:(if location not listed, please contact supervisor)"));
        existingLocationPanel.add(resultsInq);
        return existingLocationPanel;
    }

    private JPanel setupInquiryPage(){
        //Labels and inputs
        title = new JLabel("Welcome to the Inquiry Logging Page");
        dateOfInquiryLabel = new JLabel("Please enter the date of inquiry");
        infoProvidedLabel = new JLabel("Please enter any additional info provided");
        locationNameLabel = new JLabel("Enter Location Name");
        locationAddressLabel = new JLabel("Enter Location Address");
        dateOfInquiryInput = new JTextField("e.g 2004-09-09");
        infoProvidedInput = new JTextField("e.g Volunteer opportunities?");
        locationNameInput = new JTextField("e.g Telus Spark Center");
        locationAddressInput = new JTextField("e.g 123 Sesame Street");

        //Buttons
        searchVictimsButton = new JButton("Search For Victims");
        createNewPersonButton = new JButton("Create New Victim");
        submitInquiry = new JButton("Submit");

        //Panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel(new FlowLayout());
        JPanel labelPanel = new JPanel(new GridLayout(4,1));
        JPanel inputPanel = new JPanel(new GridLayout(4,1));
        JPanel buttonPanel = new JPanel(new GridLayout(3,1));

        //Add action listeners to the buttons
        createNewPersonButton.addActionListener(this);
        searchVictimsButton.addActionListener(this);
        submitInquiry.addActionListener(this);

        //Add to Panels
        headerPanel.add(title);
        labelPanel.add(dateOfInquiryLabel);
        labelPanel.add(infoProvidedLabel);
        labelPanel.add(locationNameLabel);
        labelPanel.add(locationAddressLabel);
        inputPanel.add(dateOfInquiryInput);
        inputPanel.add(infoProvidedInput);
        inputPanel.add(locationNameInput);
        inputPanel.add(locationAddressInput);
        buttonPanel.add(searchVictimsButton);
        buttonPanel.add(createNewPersonButton);
        buttonPanel.add(submitInquiry);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(labelPanel, BorderLayout.WEST);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

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
        /*Main Page Actions */
        if(e.getSource() == loginButton){
            if(centralWorker.isSelected()){
                //Just displaying a message for now, switch over to inquiry page when done
                cardLayout.show(cardPanel, "inquiry");
            }
            else if (locationWorker.isSelected()){
                //Just displaying a message for now, switch over to inquiry page when done
                cardLayout.show(cardPanel, "inquiry");
            }
            else{
                JOptionPane.showMessageDialog(this, "Please choose one of the two options");
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
            JPanel locations = this.locationSelector();
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
                        int social_id = results.getInt("social_id");
                        String name = results.getString("fname") + " " + results.getString("lname");
                        String displayString = "Social_ID: " + social_id + ", Name: " + name;
                        listModel.addElement(displayString);
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
                        int social_id = results.getInt("social_id");
                        String name = results.getString("fname") +  " " + results.getString("lname");
                        String displayString = "Social_ID: " + social_id + ", Name: " + name;
                        listModel.addElement(displayString);
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new InquiryGUI().setVisible(true);
        });
    }
    
}
