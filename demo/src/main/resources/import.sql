INSERT INTO users (first_name, last_name, username, password) VALUES ('elena', 'krunic', 'lele', 'sifra');
INSERT INTO users (first_name, last_name, username, password) VALUES ('melema', 'pantic', 'mele', 'sifra');

INSERT INTO accounts (smtp_address, smtp_port, in_server_type, in_server_address, in_server_port, username, password, display_name, user) VALUES ('smtp.gmail.com', 465, 1, 'imap.gmail.com', 993, 'korisnicko1', 'hesovana1', 'Elena', 1);
INSERT INTO accounts (smtp_address, smtp_port, in_server_type, in_server_address, in_server_port, username, password, display_name, user) VALUES ('smtp.gmail.com', 465, 1, 'imap.gmail.com', 993, 'korisnicko2', 'hesovana2', 'DrugiDisplayName', 2);
INSERT INTO accounts (smtp_address, smtp_port, in_server_type, in_server_address, in_server_port, username, password, display_name, user) VALUES ('smtp.gmail.com', 465, 1, 'imap.gmail.com', 993, 'korisnicko3', 'hesovana3', 'TreciDisplayName', 1);


INSERT INTO folders (name, parent_folder, account) VALUES ('Inbox', null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ('Outbox', null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ('Drafts', null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ('Primary', 1, 2);
INSERT INTO folders (name, parent_folder, account) VALUES ('Spam', null, 3);
INSERT INTO folders (name, parent_folder, account) VALUES ('Moj', 3, 3);


INSERT INTO messages (message_from, message_to, bcc, cc, subject, content, account, unread, folder) VALUES ('lelekrunic1@gmail.com', 'elenakrunic@gmail.com', 'bcc', '', 'Poruka 1', 'Sadrzaj poruke', 1, true, 1);
INSERT INTO messages (message_from, message_to, bcc, cc, subject, content, account, unread, folder) VALUES ('lelekrunic1@gmail.com', 'elenakrunic@gmail.com', 'bcc', '', 'Poruka 2', 'Sadrzaj druge poruke', 1, true, 2);

--INSERT INTO attachments (data, mime_type, name, message) VALUES ('data', 'application/pdf', 'simple1', 1);

--INSERT INTO message_tags (message_id, tag_id) VALUES (1,1);

INSERT INTO rules (rule_condition, rule_operation, value, folder) VALUES (1, 0, 'lelekrunic1@gmail.com', 4);

INSERT INTO tags (name, user) VALUES ('tag1', 1);

INSERT INTO photos (photo_id, path) VALUES (1,'C:/Users/lenovo/Desktop/III/OSA/osa_projekat_2020-master/loyle.jpg');

INSERT INTO contacts (display_name, email, first_name, last_name, note, photo_id, user) VALUES ('Elena', 'lelekrunic1@gmail.com', 'Elena', 'Krunic', 'note',1, 1);
insert into contacts (display_name, email, first_name, last_name, note, photo_id, user) values ('Dule','dulekovac@gmail.com','Dusan','Kovacevic','duletova biljeskica',null,1); 
insert into contacts (display_name, email, first_name, last_name, note, photo_id, user) values ('Mitar','mitarmitar@gmail.com','Mitar','Petrovic','mitrovaBiljeskica',null,1); 
insert into contacts (display_name, email, first_name, last_name, note, photo_id, user) values ('Simona','simonamosi@gmail.com','Simona','Jelic','simoninabiljeskica',null,1);
