insert into Sprint (ID, START_DATE, END_DATE) values (1,'2018-07-02','2018-07-15');
insert into Sprint (ID, START_DATE, END_DATE) values (2,'2018-07-16','2018-07-29');
insert into Sprint (ID, START_DATE, END_DATE) values (3,'2018-07-30','2018-08-05');


insert into Teammate (ID, TRIGRAM, LASTNAME, FIRSTNAME, MAIL) values (1, 'JDU', 'Duval', 'Jérôme', 'duval.je@free.fr');
insert into Teammate (ID, TRIGRAM, LASTNAME, FIRSTNAME, MAIL) values (2, 'JAB', 'AB', 'Jérôme', 'duval.je@free.fr');
insert into Teammate (ID, TRIGRAM, LASTNAME, FIRSTNAME, MAIL) values (3, 'JAC', 'AC', 'Jérôme', 'duval.je@free.fr');
insert into Teammate (ID, TRIGRAM, LASTNAME, FIRSTNAME, MAIL) values (4, 'JAD', 'AD', 'Jérôme', 'duval.je@free.fr');

insert into Planning (RANK,ROLE,TEAMMATE_ID) values (1,'RELEASE',1);
insert into Planning (RANK,ROLE,TEAMMATE_ID) values (2,'RELEASE',3);
insert into Planning (RANK,ROLE,TEAMMATE_ID) values (3,'RELEASE',4);
insert into Planning (RANK,ROLE,TEAMMATE_ID) values (1,'SCRUM',4);
insert into Planning (RANK,ROLE,TEAMMATE_ID) values (2,'SCRUM',1);
insert into Planning (RANK,ROLE,TEAMMATE_ID) values (3,'SCRUM',3);

--insert into Register (ID) values (1);