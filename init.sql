CREATE TABLE IF NOT EXISTS test (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(255) NOT NULL
);

INSERT INTO test (message)
VALUES ('Hello World'),
       ('Hola Mundo'),
       ('Bonjour le Monde'),
       ('Hallo Welt'),
       ('Hallo Wereld');
