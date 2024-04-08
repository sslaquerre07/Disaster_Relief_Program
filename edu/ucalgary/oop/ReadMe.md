# User Guide ENSF380 IA#3

## This project includes all of the required java files, all interconnected with the OOP implemented throughout the course
---
## User Interface Guide
Both user interfaces were implemented using the GUI features shown in the Week 12 Videos
*Note: Both types of users can enter DisasterVictims and Inquiries, the main diffrence is that the central worker can enter these things for all locations*
The application is accessible through 2 classes:
### CentralWorker.java
By navigating to this file, you can compile and run the main function. This will allow you to pick from either of the two interfaces. From a central worker perspective, both interaces allow for you to choose where your inquiry is being logged, or where your disaster victim is stored. 
### LocationWorker.java
Running the main function in this file will first prompt you to select from all of the current locations in the database. You will have to choose a valid id, if not you will be prompted to pick one again. Once you pick one, you will be prompted with the same two interfaces given to the central worker, with the location already set for database uploading. 
## Interfaces Breakdown
### DisasterVictimLogging GUI
This interface was broken down into several components to allow a DisasterVictim to be created as well as possible. This interface has multiple components, each of which will be described below
1. Generic Detail input: Take down all general info about the DisasterVictim that is not stored in the form of an array.
2. Family Relations Page: Allows the user to either select an existing DisasterVictim or create a new one to be related to the DisasterVictim being created.
3. Supply Page: Allows the user to allocate supplies to the disasterVictim in question, including both the type and quantity of these supplies.
4. Medical Records Page: Allows users to create MedicalRecords for the DisasterVictim, either at an existing location or a new one. 
5. Submit Button: Hitting the submit button will upload the victim and all of its components to the database, feel free to check for yourself once you've submitted one!
### InquiryLoggingGUI
This interface is also broken down into several components, meant to replicate the creation of Inquiries clearly.
1. Generic Inquiry Input: Take down all general inquiry info, and store it upon submission. The only unique thing about this interface is that upon submission, it will take the inquirer info and if it exists in the database, it will use it, if not, it will create a new one.
2. Search For Vicitm Page: Inquiries can either be related to a disasterVictim, or just a general inquiry. If you want to link the inquiry to a vicitm, you must do so in this page. You can search either with the first name or with first and last name. It will search both to see if the corresponding name contains the strings put in as inputs. By selecting a DisasterVictim, it successfully links the inquiry upon submission.
3. Create a new Victim Button: If you try searching for a victim and it does not seem to be in the database, you can create a new victim. This simply opens a window to the other interface, allowing you to create a new victim. Once the new victim has been created, it will return you to the original window, and you can search and select the victim you just created. 
4. Submit Button: Hitting the submit button will upload the inquiry and all of its newly created parts to the database, feel free to check for yourself once you've submitted one!
## Additional Information
- GenderOptions.txt FilePath: The GenderOptions text file should work universally across all OS's. The filepath is gotten from line 289 in DisasterVictim.java. The only caveat is that you will have to place the text file in the edu/ucalgary/oop directory
- Test file locations: The test files are all in the same directory as the java files, as per instructed in the rubric
- javadoc creation: I was having some trouble creating the javadoc files all at once with a *.java format since it looks like something is the test files does not transfer properly to javadoc, but the it works for all of the regular class files.
- **IMPORTANT NOTE** : In the project.sql, I already included all the permissions needed for the oop user at the bottom of the file, this is assuming that oop will not be the one creating the database (as was the case for myself). If this is the case, please uncomment the grant statements at the bottom (CTRL + backslash) before running the .sql file. This will save you a lot of time in permission allowing (as was also the case for myself).  
- Thank you for taking the time to read through the documentation and grading my assignment, I hope you have an amazing summer!
