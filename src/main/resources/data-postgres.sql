DELETE FROM comments;
DELETE FROM books;
DELETE FROM authors;
DELETE FROM genres;
ALTER SEQUENCE authors_id_seq RESTART WITH 1;
ALTER SEQUENCE genres_id_seq RESTART WITH 1;
ALTER SEQUENCE books_id_seq RESTART WITH 1;
ALTER SEQUENCE comments_id_seq RESTART WITH 1;

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
