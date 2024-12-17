CREATE DATABASE IF NOT EXISTS pdfwatermark;
USE pdfwatermark;

CREATE TABLE chunk_files (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fileId INT NOT NULL,
    chunkIndex INT NOT NULL,
    totalChunks INT NOT NULL,
    data LONGBLOB NOT NULL
);

CREATE TABLE pdf_files (
    id INT PRIMARY KEY,
    fileName VARCHAR(255) NOT NULL,
    userId INT NOT NULL,
    lastModified TIMESTAMP NOT NULL
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    fullName VARCHAR(100) NOT NULL
);

INSERT INTO users (username, password, fullName)
VALUES 
('admin', '123', 'Super User'),
('user1', 'password1', 'User num1');


CREATE TABLE temppdfids (
    id INT PRIMARY KEY,
    fileId INT NOT NULL
);

INSERT INTO temppdfids (id,fileId)
VALUES
(1,1);
