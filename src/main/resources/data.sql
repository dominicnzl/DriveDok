insert into DRIVE_DOK_USER (id, name, email, password)
values (1, 'Henk', 'abc@xyz.nl', 'pw'),
       (2, 'Ingrid', 'bcd@jkl.nl', 'pw2');

insert into PARKING_ZONE (id, name, version, total_parking_spots) values (1, 'Noord', NOW(), 100);
insert into PARKING_ZONE (id, name, version, total_parking_spots) values (2, 'Oost', NOW(), 50);
insert into PARKING_ZONE (id, name, version, total_parking_spots) values (3, 'Zuid', NOW(), 80);
insert into PARKING_ZONE (id, name, version, total_parking_spots) values (4, 'West', NOW(), 150);