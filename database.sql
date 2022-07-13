-- Execute as root user

CREATE USER misr_assignment@localhost IDENTIFIED BY 'M1sr_4ss1gnm3nt';

CREATE DATABASE misr_assignment;

GRANT ALL PRIVILEGES ON misr_assignment.* TO misr_assignment@localhost WITH GRANT OPTION;

FLUSH PRIVILEGES;

USE misr_assignment;

CREATE TABLE Area_Type (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO Area_Type (name)
VALUES ('Corn'),
       ('Rice'),
       ('Wheat'),
       ('Cotton');

CREATE TABLE Plot_Of_Land (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    amount_of_water INT NOT NULL,
    time_slots INT NOT NULL,
    area_type_id INT,
    status ENUM('IDLE', 'IRRIGATING') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (area_type_id) REFERENCES Area_Type (id) ON DELETE CASCADE
);

INSERT INTO Plot_Of_Land (name, amount_of_water, time_slots, area_type_id, status)
VALUES ('Corn Field 1', 3000, 10, (SELECT id FROM Area_Type WHERE name = 'Corn'), 'IDLE'),
       ('Corn Field 2', 5000, 15, (SELECT id FROM Area_Type WHERE name = 'Corn'), 'IDLE'),
       ('Corn Field 3', 2000, 20, (SELECT id FROM Area_Type WHERE name = 'Corn'), 'IDLE'),
       ('Rice Field 1', 20000, 10, (SELECT id FROM Area_Type WHERE name = 'Rice'), 'IDLE'),
       ('Rice Field 2', 25000, 15, (SELECT id FROM Area_Type WHERE name = 'Rice'), 'IDLE'),
       ('Wheat Field 1', 3000, 10, (SELECT id FROM Area_Type WHERE name = 'Wheat'), 'IDLE'),
       ('Cotton Field 1', 3000, 10, (SELECT id FROM Area_Type WHERE name = 'Cotton'), 'IDLE');