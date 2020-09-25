INSERT INTO users (first_name, last_name, username, password) VALUES ('elena', 'krunic', 'lele', 'sifra');

INSERT INTO accounts (smtp_address, smtp_port, in_server_type, in_server_address, in_server_port, username, password, display_name, user) VALUES ('smtp.gmail.com', 465, 1, 'imap.gmail.com', 993, 'lelekrunic', 'rarasosa3!', 'Elena', 1);

INSERT INTO folders (name, parent_folder, account) VALUES ('Inbox', null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ('Outbox', null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ('Drafts', null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ('Primary', 1, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ('Spam', 1, 1);

INSERT INTO rules (rule_condition, rule_operation, value, folder) VALUES (1, 0, 'lelekrunic@gmail.com', 4);

INSERT INTO contacts (display_name, email, first_name, last_name, note, user) VALUES ('Elena', 'lelekrunic1@gmail.com', 'Elena', 'Krunic', 'note', 1);
insert into contacts (display_name,email,first_name,last_name,note,user) values ('Dule','dulekovac@gmail.com','Dusan','Kovacevic','njemacki penzioner',1); 
insert into contacts (display_name,email,first_name,last_name,note,user) values ('Lele','elenapicto@gmail.com','Elena','Krunic','note',1); 
insert into contacts (display_name,email,first_name,last_name,note,user) values ('Dule','dulekovac@gmail.com','Dusan','Kovacevic','njemacki penzioner',1); 

INSERT INTO photos (path, contact) VALUES ('image3.jpg', 1);

INSERT INTO tags (name, user) VALUES ('tag1', 1);

INSERT INTO messages (message_from, message_to, bcc, cc, subject, content, account, unread, folder) VALUES ('lelekrunic@gmail.com', 'elenakrunic@gmail.com', 'bcc', '', 'Poruka 1', 'Sadrzaj poruke', 1, true, 1);

INSERT INTO attachments (data, mime_type, name, message) VALUES ('data', 'application/pdf', 'simple1', 1);

INSERT INTO message_tags (message_id, tag_id) VALUES (1,1);
