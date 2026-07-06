DELETE FROM comments;
DELETE FROM books;
DELETE FROM authors;
DELETE FROM genres;
DELETE FROM users;
ALTER TABLE comments ALTER COLUMN id RESTART WITH 1;
ALTER TABLE authors ALTER COLUMN id RESTART WITH 1;
ALTER TABLE genres ALTER COLUMN id RESTART WITH 1;
ALTER TABLE books ALTER COLUMN id RESTART WITH 1;
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

INSERT INTO authors (name) VALUES ('Leo Tolstoy');
INSERT INTO authors (name) VALUES ('Fyodor Dostoevsky');
INSERT INTO authors (name) VALUES ('Anton Chekhov');
INSERT INTO authors (name) VALUES ('Alexander Pushkin');
INSERT INTO authors (name) VALUES ('Mikhail Bulgakov');

INSERT INTO genres (name) VALUES ('Novel');
INSERT INTO genres (name) VALUES ('Short Story');
INSERT INTO genres (name) VALUES ('Drama');
INSERT INTO genres (name) VALUES ('Poetry');
INSERT INTO genres (name) VALUES ('Fantasy');

INSERT INTO books (title, author_id, genre_id) VALUES ('War and Peace', 1, 1);
INSERT INTO books (title, author_id, genre_id) VALUES ('Anna Karenina', 1, 1);
INSERT INTO books (title, author_id, genre_id) VALUES ('Crime and Punishment', 2, 1);
INSERT INTO books (title, author_id, genre_id) VALUES ('The Brothers Karamazov', 2, 1);
INSERT INTO books (title, author_id, genre_id) VALUES ('The Cherry Orchard', 3, 3);
INSERT INTO books (title, author_id, genre_id) VALUES ('Eugene Onegin', 4, 4);
INSERT INTO books (title, author_id, genre_id) VALUES ('The Master and Margarita', 5, 5);

-- password: password (BCrypt encoded)
INSERT INTO users (username, password, role) VALUES ('admin', '$2a$10$DbPs2C3IWeVqWcSgjiVf5ei9eFSjiBfsGEFb3dC61Y7GJdOm.vsri', 'ROLE_ADMIN');
INSERT INTO users (username, password, role) VALUES ('user', '$2a$10$DbPs2C3IWeVqWcSgjiVf5ei9eFSjiBfsGEFb3dC61Y7GJdOm.vsri', 'ROLE_USER');
