/*
 Each time this file is executed, it will reset the database to the original state defined below.
 You can import this directly in your database by (a) manually entering the first three lines of
 commands form this file, (b) removing the first three lines of commands from this file, and
 (c) \i 'path_to_file\project.sql' (with appropriate use of \ or / based on OS).

 During grading, TAs will assume that these two tables exist, but will enter different values.
 Thus you cannot assume that any of the values provided here exist, but you can assume the tables
 exist.

 You may optionally create additional tables in the ensf380project database with demonstration 
 data, provided that you provide the information in a valid SQL file which TAs can import and
 clearly include this information in your instructions.
 */


DROP DATABASE IF EXISTS ensf380project;
CREATE DATABASE ensf380project;
\c ensf380project


CREATE TABLE INQUIRER (
    id SERIAL PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50),
    phoneNumber VARCHAR(20) NOT NULL
);
INSERT INTO INQUIRER (firstName, lastName, phoneNumber) VALUES
('Dominik', 'Pflug', '123-456-9831'),
('Yaa', 'Odei', '123-456-8913'),
('Cecilia', 'Cobos', '123-456-7891'),
('Hongjoo', 'Park', '123-456-8912'),
('Saartje', 'Ynag', '123-456-7234'),
('Urjoshi', 'Lag', '456-123-4281');

CREATE TABLE LOCATION_TABLE(
    location_id SERIAL PRIMARY KEY,
    name VARCHAR(5000),
    address VARCHAR(5000)
);

INSERT INTO LOCATION_TABLE (name, address) VALUES
('Office A', '123 Main St, Anytown, USA'),
('Warehouse B', '456 Elm St, Somewhereville, USA'),
('Store C', '789 Oak St, Anywhere City, USA'),
('Factory D', '321 Pine St, Nowhereville, USA'),
('Shop E', '654 Maple St, Anyplace Town, USA'),
('Center F', '987 Cedar St, Everywhere City, USA'),
('Facility G', '135 Walnut St, Anytown, USA'),
('Building H', '246 Birch St, Anywhereville, USA'),
('Outlet I', '357 Spruce St, Somewhere City, USA'),
('Complex J', '468 Fir St, Anyplace, USA');

CREATE TABLE DISASTER_VICTIM (
    social_id SERIAL PRIMARY KEY,
    fName VARCHAR(500) NOT NULL,
    lName VARCHAR(500) NOT NULL,
    dob DATE,
    age INT,
    location_id INT NOT NULL,
    FOREIGN KEY (location_id) REFERENCES LOCATION_TABLE(location_id) ON UPDATE CASCADE
);

INSERT INTO DISASTER_VICTIM (fName, lName, dob, age, location_id) VALUES
    ('John', 'Doe', '1990-01-15', NULL, 1),
    ('Jane', 'Smith', NULL, 36, 2),
    ('Michael', 'Johnson', '1978-08-10', NULL, 3),
    ('Emily', 'Williams', NULL, 27, 4),
    ('Christopher', 'Brown', '1982-11-08', NULL, 5),
    ('Amanda', 'Jones', NULL, 21, 6),
    ('David', 'Garcia', NULL, 23, 7),
    ('Jessica', 'Martinez', '1973-04-18', NULL, 8),
    ('Daniel', 'Hernandez', NULL, 34, 9),
    ('Sarah', 'Lopez', '1992-02-28', NULL, 10),
    ('Matthew', 'Scott', NULL, 42, 10),
    ('Ashley', 'Lee', '1997-10-22', NULL, 9),
    ('James', 'Young', NULL, 47, 9),
    ('Taylor', 'White', NULL, 25, 7),
    ('Olivia', 'Thomas', '1989-12-20', NULL, 6);

CREATE TABLE MEDICAL_RECORD(
    record_id SERIAL PRIMARY KEY,
    date_of_treatment DATE NOT NULL,
    treatment_detials VARCHAR(5000) NOT NULL,
    location_id INT NOT NULL,
    social_id INT NOT NULL,
    FOREIGN KEY (location_id) REFERENCES LOCATION_TABLE(location_id) ON UPDATE CASCADE,
    FOREIGN KEY (social_id) REFERENCES DISASTER_VICTIM(social_id) ON UPDATE CASCADE
);

INSERT INTO MEDICAL_RECORD (date_of_treatment, treatment_detials, location_id, social_id) VALUES
    ('2023-01-05', 'Broken leg', 1, 1),
    ('2023-02-10', 'Flu treatment', 2, 2),
    ('2023-03-15', 'Allergy medication', 3, 3),
    ('2023-04-20', 'Physical therapy', 4, 4),
    ('2023-05-25', 'Dental checkup', 5, 5),
    ('2023-06-30', 'Eye examination', 6, 6),
    ('2023-07-05', 'MRI scan', 7, 7),
    ('2023-08-10', 'X-ray', 8, 8),
    ('2023-09-15', 'Blood test', 9, 9),
    ('2023-10-20', 'Ultrasound', 10, 10),
    ('2023-11-25', 'Physical examination', 9, 10),
    ('2023-12-30', 'Vaccination', 9, 7),
    ('2024-01-05', 'Counseling session', 1, 8),
    ('2024-02-10', 'Medication refill', 2, 6),
    ('2024-03-15', 'Diet consultation', 3, 4),
    ('2024-04-20', 'Acupuncture', 4, 3),
    ('2024-05-25', 'Chiropractic adjustment', 5, 8),
    ('2024-06-30', 'Psychotherapy session', 6, 9),
    ('2024-07-05', 'Electrocardiogram (ECG)', 7, 10),
    ('2024-08-10', 'Dialysis treatment', 8, 8);


CREATE TABLE FAMILY_RELATIONS(
    realtionship_id SERIAL PRIMARY KEY,
    person1ID INT NOT NULL,
    person2ID INT NOT NULL,
    relationship VARCHAR(5000) NOT NULL,
    FOREIGN KEY (person1ID) REFERENCES DISASTER_VICTIM(social_id) ON UPDATE CASCADE,
    FOREIGN KEY (person2ID) REFERENCES DISASTER_VICTIM(social_id) ON UPDATE CASCADE
);

CREATE TABLE INQUIRY_LOG (
    id SERIAL PRIMARY KEY,
    inquirer int NOT NULL,
    callDate DATE NOT NULL,
    details VARCHAR(500) NOT NULL,
    location_id int NOT NULL,
    social_id int,
    foreign key (inquirer) references INQUIRER(id) ON UPDATE CASCADE,
    foreign key (location_id) references LOCATION_TABLE(location_id) ON UPDATE CASCADE,
    foreign key (social_id) references DISASTER_VICTIM(social_id) ON UPDATE CASCADE
);

INSERT INTO INQUIRY_LOG (inquirer, callDate, details, location_id, social_id) VALUES
(1, '2024-02-28', 'Theresa Pflug', 1, NULL),
(2, '2024-02-28', 'Offer to assist as volunteer', 2, NULL),
(3, '2024-03-01', 'Valesk Souza', 3, NULL),
(1, '2024-03-01', 'Theresa Pflug', 4, NULL),
(1, '2024-03-02', 'Theresa Pflug', 5, NULL),
(4, '2024-03-02', 'Yoyo Jefferson and Roisin Fitzgerald', 6, NULL),
(5, '2024-03-02', 'Henk Wouters', 7, NULL),
(3, '2024-03-03', 'Melinda', 8, NULL),
(6, '2024-03-04', 'Julius', 9, NULL),
(1, '2024-03-05', 'Theresa Pflug', 10, NULL),
(2, '2024-03-06', 'Offer to assist as volunteer', 1, NULL),
(4, '2024-03-06', 'Yoyo Jefferson and Roisin Fitzgerald', 2, NULL),
(5, '2024-03-07', 'Henk Wouters', 3, NULL),
(3, '2024-03-08', 'Melinda', 4, NULL),
(6, '2024-03-09', 'Julius', 5, NULL);

CREATE TABLE SUPPLY (
    collection_id SERIAL PRIMARY KEY,
    supply_type VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    social_id INT NOT NULL,
    FOREIGN KEY (social_id) REFERENCES DISASTER_VICTIM(social_id) ON UPDATE CASCADE
);

INSERT INTO SUPPLY(supply_type, quantity, social_id) VALUES
('Bottled Water', 150, 1),
('Canned Food', 150, 2),
('First Aid Kits', 100, 3),
('Blankets', 120, 4),
('Flashlights', 80, 5),
('Batteries', 100, 6),
('Tents', 20, 7),
('Clothing', 130, 8),
('Medicines', 90, 9),
('Hygiene Kits', 70, 10),
('Emergency Shelter Tarps', 50, 11),
('Cooking Stoves', 80, 12),
('Sleeping Bags', 70, 13),
('Chainsaws', 15, 14),
('Power Generators', 10, 15),
('Portable Toilets', 50, 1),
('Baby Formula', 60, 2),
('Mosquito Repellent', 70, 3),
('Emergency Radios', 40, 4),
('Water Purification Tablets', 100, 5);


/* Done for privilege accesses, ask a TA before submitting */
GRANT ALL PRIVILEGES ON DATABASE ensf380project TO oop;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO oop;
GRANT ALL PRIVILEGES ON SEQUENCE inquirer_id_seq TO oop;
GRANT ALL PRIVILEGES ON SEQUENCE location_table_location_id_seq TO oop;
GRANT ALL PRIVILEGES ON SEQUENCE disaster_victim_social_id_seq TO oop;
GRANT ALL PRIVILEGES ON SEQUENCE medical_record_record_id_seq TO oop;
GRANT ALL PRIVILEGES ON SEQUENCE family_relations_realtionship_id_seq TO oop;
GRANT ALL PRIVILEGES ON SEQUENCE inquiry_log_id_seq TO oop;
GRANT ALL PRIVILEGES ON SEQUENCE supply_collection_id_seq TO oop;