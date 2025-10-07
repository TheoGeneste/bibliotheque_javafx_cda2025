CREATE TABLE books(
   id INT AUTO_INCREMENT,
   title VARCHAR(255) NOT NULL,
   release_date DATE,
   isAvailable TINYINT(1),
   PRIMARY KEY(id)
);

CREATE TABLE author(
   id INT AUTO_INCREMENT,
   lastname VARCHAR(255) NOT NULL,
   firstname VARCHAR(255),
   born_at DATE,
   PRIMARY KEY(id)
);

CREATE TABLE editor(
   id INT AUTO_INCREMENT,
   label VARCHAR(255),
   created_at DATE,
   PRIMARY KEY(id)
);

CREATE TABLE users(
   id INT AUTO_INCREMENT,
   email VARCHAR(200) NOT NULL,
   password TEXT NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(email)
);

CREATE TABLE client(
   id INT AUTO_INCREMENT,
   lastname VARCHAR(255) NOT NULL,
   firstname VARCHAR(255),
   email VARCHAR(200),
   PRIMARY KEY(id),
   UNIQUE(email)
);

CREATE TABLE gender(
   id INT AUTO_INCREMENT,
   label VARCHAR(255) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE distribute(
   book_id INT AUTO_INCREMENT,
   editor_id INT,
   PRIMARY KEY(book_id, editor_id),
   FOREIGN KEY(book_id) REFERENCES books(id),
   FOREIGN KEY(editor_id) REFERENCES editor(id)
);

CREATE TABLE writes(
   book_id INT,
   author_id INT,
   PRIMARY KEY(book_id, author_id),
   FOREIGN KEY(book_id) REFERENCES books(id),
   FOREIGN KEY(author_id) REFERENCES author(id)
);

CREATE TABLE borrow(
   book_id INT,
   client_id INT,
   end_date DATE NOT NULL,
   start_date DATE NOT NULL,
   isDone TINYINT(1),
   PRIMARY KEY(book_id, client_id),
   FOREIGN KEY(book_id) REFERENCES books(id),
   FOREIGN KEY(client_id) REFERENCES client(id)
);

CREATE TABLE have(
   book_id INT,
   gender_id INT,
   PRIMARY KEY(book_id, gender_id),
   FOREIGN KEY(book_id) REFERENCES books(id),
   FOREIGN KEY(gender_id) REFERENCES gender(gender_id)
);
