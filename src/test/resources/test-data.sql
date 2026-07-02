DELETE FROM comments;
DELETE FROM books;
DELETE FROM authors;
DELETE FROM genres;
ALTER TABLE comments ALTER COLUMN id RESTART WITH 1;
ALTER TABLE authors ALTER COLUMN id RESTART WITH 1;
ALTER TABLE genres ALTER COLUMN id RESTART WITH 1;
ALTER TABLE books ALTER COLUMN id RESTART WITH 1;

INSERT INTO authors (name) VALUES ('Test Author 1');
INSERT INTO authors (name) VALUES ('Test Author 2');

INSERT INTO genres (name) VALUES ('Test Genre 1');
INSERT INTO genres (name) VALUES ('Test Genre 2');

-- ID будут сгенерированы автоматически (1 и 2)
INSERT INTO books (title, author_id, genre_id) VALUES ('Test Book 1', 1, 1);
INSERT INTO books (title, author_id, genre_id) VALUES ('Test Book 2', 1, 2);
INSERT INTO books (title, author_id, genre_id) VALUES ('Test Book 3', 2, 1);
