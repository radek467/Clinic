CREATE TABLE if not exists offices (
    office_ID int NOT NULL primary key auto_increment,
    room_number int NOT NULL
);



CREATE TABLE if not exists specializations (
    specialization_ID int(11) NOT NULL auto_increment primary key,
    specialization_type varchar(50) NOT NULL
);


create table if not exists doctors(
    doctor_ID integer not null auto_increment primary key,
    name varchar(50) not null,
    surname varchar (50) not null,
    specialization_ID int default null,
    office_ID int default null,
    FOREIGN KEY (specialization_ID) REFERENCES specializations(specialization_ID),
    FOREIGN KEY (office_ID) REFERENCES offices(office_ID)

);


CREATE TABLE if not exists services (
    service_ID int NOT NULL primary key auto_increment,
    title varchar NOT NULL,
    price float NOT NULL,
    duration int NOT NULL,
    specialization_ID int DEFAULT NULL,
    FOREIGN KEY (specialization_ID) REFERENCES specializations(specialization_ID)
);



CREATE TABLE if not exists patients(
    patient_ID int NOT NULL auto_increment primary key,
    name varchar NOT NULL,
    surname varchar NOT NULL,
    pesel varchar(12) NOT NULL,
    phone_number varchar(9),
    email varchar
);

CREATE TABLE if not exists visits (
    visit_ID int(11) NOT NULL primary key auto_increment,
    visit_date datetime NOT NULL,
    visit_description varchar(1000) DEFAULT NULL,
    rating float DEFAULT ZERO(),
    doctor_ID int(11) DEFAULT NULL,
    patient_ID int(11) DEFAULT NULL,
    service_ID int(11) DEFAULT NULL,
    FOREIGN KEY (doctor_ID) REFERENCES doctors(doctor_ID),
    FOREIGN KEY (patient_ID) REFERENCES patients(patient_ID),
    FOREIGN KEY (service_ID) REFERENCES services(service_ID)
);