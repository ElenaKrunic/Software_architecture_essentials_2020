INSERT INTO users (first_name, last_name, username, password) VALUES ('elena', 'krunic', 'lele', 'sifra');
INSERT INTO users (first_name, last_name, username, password) VALUES ('melema', 'pantic', 'mele', 'sifra');

INSERT INTO accounts (smtp_address, smtp_port, in_server_type, in_server_address, in_server_port, username, password, display_name, user) VALUES ('smtp.gmail.com', 587, 1, 'imap.gmail.com', 993, 'lelekrunic1@gmail.com', 'pereCvetka!8', 'Elena', 1);
INSERT INTO accounts (smtp_address, smtp_port, in_server_type, in_server_address, in_server_port, username, password, display_name, user) VALUES ('smtp.gmail.com', 587, 1, 'imap.gmail.com', 993, 'korisnicko2', 'hesovana2', 'DrugiDisplayName', 2);
INSERT INTO accounts (smtp_address, smtp_port, in_server_type, in_server_address, in_server_port, username, password, display_name, user) VALUES ('smtp.gmail.com', 587, 1, 'imap.gmail.com', 993, 'elenakrunic@gmail.com', 'Bijelazavjesaa', 'TreciDisplayName', 1);

INSERT INTO folders (name, parent_folder, account) VALUES ('Inbox', null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ('Outbox', null, 2);
INSERT INTO folders (name, parent_folder, account) VALUES ('Drafts', null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ('Primary', 1, 2);
INSERT INTO folders (name, parent_folder, account) VALUES ('Spam', null, 3);
INSERT INTO folders (name, parent_folder, account) VALUES ('Sent', null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ('Moj', 3, 3);

INSERT INTO messages (message_from, message_to, cc, bcc, date_time, subject, content, account, unread, folder) VALUES ('lelekrunic1@gmail.com', 'elenakrunic@gmail.com', 'ccPoruke1', 'bccPoruke1', '2020-12-25 12:59:00', 'SubjectPoruke1Naloga1', 'ContentPoruke1Naloga1', 1, true, 1);
INSERT INTO messages (message_from, message_to, cc, bcc, date_time, subject, content, account, unread, folder) VALUES ('elenakrunic@gmail.com', 'lelekrunic1@gmail.com', 'ccPoruke2', 'bccPoruke2', '2020-12-26 19:02:00', 'SubjectPoruke2Naloga2', 'ContentPoruke2Naloga2', 2, true, 2);

--INSERT INTO attachments (data, mime_type, name, message) VALUES ('C:/Users/lenovo/Desktop/Desktop/III/OSA/osaGitRepo/osa_projekat_2020/loyle.jpg', 'image/bmp', 'loyle slika', 1);

INSERT INTO tags (name, user) VALUES ('tag1', 1);

INSERT INTO message_tags (message_id, tag_id) VALUES (1,1);

--INSERT INTO rules (rule_condition, rule_operation, value, folder) VALUES (1, 0, 'lelekrunic1@gmail.com', 4);
INSERT INTO rules (rule_condition, rule_operation, value, source_folder_id,destination_folder_id ) VALUES (1, 0, 'lelekrunic1@gmail.com', 1, 4);


--INSERT INTO photos (path) VALUES (1, 'C:/Users/lenovo/Desktop/Desktop/III/OSA/osaGitRepo/osa_projekat_2020/loyle.jpg');
--INSERT INTO PHOTOS(photo_id, path) VALUES(1, 'C:/Users/lenovo/Desktop/III/OSA/osa_projekat_2020-master/loyle.jpg')
INSERT INTO PHOTOS(photo_id, path) VALUES(1, 'C:/Users/draga/OneDrive/Desktop/sb.jpg')

INSERT INTO contacts (display_name, email, first_name, last_name, note, photo_id, user) VALUES ('Elena', 'lelekrunic1@gmail.com', 'Elena', 'Krunic', 'note',1, 1);
insert into contacts (display_name, email, first_name, last_name, note, photo_id, user) values ('Dule','dulekovac@gmail.com','Dusan','Kovacevic','duletova biljeskica',null,1); 
insert into contacts (display_name, email, first_name, last_name, note, photo_id, user) values ('Mitar','mitarmitar@gmail.com','Mitar','Petrovic','mitrovaBiljeskica',null,1); 
insert into contacts (display_name, email, first_name, last_name, note, photo_id, user) values ('Simona','simonamosi@gmail.com','Simona','Jelic','simoninabiljeskica',null,1);
