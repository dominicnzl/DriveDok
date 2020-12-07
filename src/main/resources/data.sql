insert into DRIVE_DOK_USER (id, name, email, password)
values (1, 'Henk', 'abc@xyz.nl', 'pw'),
       (2, 'Ingrid', 'bcd@jkl.nl', 'pw2');

insert into PARKING_ZONE (id, name, version) values (1, 'Noord', NOW());
insert into PARKING_ZONE (id, name, version) values (2, 'Oost', NOW());
insert into PARKING_ZONE (id, name, version) values (3, 'Zuid', NOW());
insert into PARKING_ZONE (id, name, version) values (4, 'West', NOW());