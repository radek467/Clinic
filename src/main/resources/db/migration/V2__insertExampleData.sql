insert into offices (room_number) values (1);
insert into offices (room_number) values (2);
insert into offices (room_number) values (3);


insert into specializations (specialization_type) values ( 'surgeon' );
insert into specializations (specialization_type) values ( 'dentist' );
insert into specializations (specialization_type) values ( 'vet' );


insert into doctors (name, surname, specialization_ID) values ( 'Radek', 'Kraj', 1 );
insert into doctors (name, surname, specialization_ID) values ( 'Szymon', 'Lorenc', 2 );
insert into doctors (name, surname, specialization_ID) values ( 'Michał', 'Łożański', 3 );

insert into patients (name, surname, pesel, phone_number, email) values ( 'Radosław', 'Kraj', '00000000000', '000000000', 'r@.pl' );


insert into services(price, title, duration, specialization_ID) values (1500 , 'kidney excision', 280, 1);
insert into services(price, title, duration, specialization_ID) values (2000 , 'leg amputation', 240, 1);
insert into services(price, title, duration, specialization_ID) values (200 , 'tooth extraction', 60, 2);
insert into services(price, title, duration, specialization_ID) values (100 , 'control visit', 20, 2);
insert into services(price, title, duration, specialization_ID) values (50 , 'pet the dog', 40, 3);
insert into services(price, title, duration, specialization_ID) values (50 , 'control visit', 20, 3);

insert into visits(visit_date, visit_description, doctor_ID, patient_ID, service_ID, rating) values ('2020-06-01 8:30:00', 'first', 1, 1, 1, 4);
insert into visits(visit_date, visit_description, doctor_ID, patient_ID, service_ID, rating) values ('2020-06-01 14:00:00', 'second', 1, 1, 2, 5);

