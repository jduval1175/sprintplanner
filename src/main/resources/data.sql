--insert into Sprint values (1,10,'2018-07-07');
insert into Sprint (ID, DAYS, START_DATE) values (1,10,'2018-07-07');


insert into Teammate (ID, TRIGRAM, LASTNAME, FIRSTNAME, MAIL) values (1, 'JDU', 'Duval', 'Jérôme', 'duval.je@free.fr');
insert into Teammate (ID, TRIGRAM, LASTNAME, FIRSTNAME, MAIL) values (2, 'JAB', 'AB', 'Jérôme', 'duval.je@free.fr');
insert into Teammate (ID, TRIGRAM, LASTNAME, FIRSTNAME, MAIL) values (3, 'JAC', 'AC', 'Jérôme', 'duval.je@free.fr');
insert into Teammate (ID, TRIGRAM, LASTNAME, FIRSTNAME, MAIL) values (4, 'JAD', 'AD', 'Jérôme', 'duval.je@free.fr');


insert into Role (ID, name) values (1, 'Developer');
insert into Role (ID, name) values (2, 'Scrum Master');
insert into Role (ID, name) values (3, 'Release');
insert into Role (ID, name) values (4, 'Support');