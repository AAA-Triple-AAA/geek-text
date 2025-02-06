-- Create publisher, author, and book first because of their close relationships
CREATE TABLE publisher
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE TABLE author
(
    id           SERIAL PRIMARY KEY,
    name_first   VARCHAR(255) NOT NULL,
    name_last    VARCHAR(255) NOT NULL,
    biography    TEXT,
    publisher_id INTEGER      REFERENCES publisher (id) ON DELETE SET NULL
);
CREATE TABLE book
(
    id           SERIAL PRIMARY KEY,
    isbn         VARCHAR(13)  NOT NULL,
    name         VARCHAR(255) NOT NULL,
    description  TEXT,
    year         INTEGER,
    price        DECIMAL(10, 2),
    discount     DECIMAL(5, 2),
    copies_sold  INTEGER,
    rating       DECIMAL(3, 2),
    genre        VARCHAR(255),
    publisher_id INTEGER      REFERENCES publisher (id) ON DELETE SET NULL,
    author_id    INTEGER      REFERENCES author (id) ON DELETE SET NULL
);

-- Create user and credit card because of their close relationship
CREATE TABLE "user"
(
    id             SERIAL PRIMARY KEY,
    username       VARCHAR(255) NOT NULL UNIQUE,
    password       VARCHAR(255) NOT NULL,
    name_first     VARCHAR(255) NOT NULL,
    name_last      VARCHAR(255) NOT NULL,
    email          VARCHAR(255) NOT NULL UNIQUE,
    address_street VARCHAR(255),
    address_city   VARCHAR(255),
    address_state  VARCHAR(255),
    address_zip    VARCHAR(20)
);
CREATE TABLE credit_card
(
    id          SERIAL PRIMARY KEY,
    card_holder VARCHAR(255) NOT NULL,
    number      VARCHAR(16)  NOT NULL,
    cvv         VARCHAR(3)   NOT NULL,
    zip         VARCHAR(20)  NOT NULL,
    user_id     INTEGER REFERENCES "user" (id) ON DELETE CASCADE
);

-- Create shopping cart relation table
CREATE TABLE has_in_cart
(
    user_id INTEGER REFERENCES "user" (id) ON DELETE CASCADE,
    book_id INTEGER REFERENCES book (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, book_id)
);

-- Create rating and commenting together because they are closely related features
CREATE TABLE rating
(
    id        SERIAL PRIMARY KEY,
    rating    INTEGER CHECK (rating >= 1 AND rating <= 5) NOT NULL,
    datestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id   INTEGER REFERENCES "user" (id) ON DELETE CASCADE,
    book_id   INTEGER REFERENCES book (id) ON DELETE CASCADE
);
CREATE TABLE comment
(
    id        SERIAL PRIMARY KEY,
    comment   TEXT NOT NULL,
    datestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id   INTEGER REFERENCES "user" (id) ON DELETE CASCADE,
    book_id   INTEGER REFERENCES book (id) ON DELETE CASCADE
);

-- Create wish_list related tables
CREATE TABLE wish_list
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    user_id INTEGER REFERENCES "user" (id) ON DELETE CASCADE
);
CREATE TABLE is_in_list
(
    wish_list_id INTEGER REFERENCES wish_list (id) ON DELETE CASCADE,
    book_id      INTEGER REFERENCES book (id) ON DELETE CASCADE,
    PRIMARY KEY (wish_list_id, book_id)
);