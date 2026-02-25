CREATE TABLE IF NOT EXISTS hint
(
    id          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    hint_content VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO hint (hint_content)
VALUES ('picasso'),
       ('dier'),
       ('film');
