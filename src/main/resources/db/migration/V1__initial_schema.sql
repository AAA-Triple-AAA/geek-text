-- Create publisher, author, and book first because of their close relationships
CREATE TABLE publisher
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    discount DECIMAL(5, 2)
);

CREATE TABLE author
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    biography TEXT,
    publisher_id INTEGER REFERENCES publisher (id) ON DELETE SET NULL
);

CREATE TABLE genre
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE book
(
    isbn VARCHAR(13) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    year INTEGER,
    price DECIMAL(10, 2),
    copies_sold INTEGER,
    genre_id INTEGER REFERENCES genre (id) ON DELETE SET NULL,
    publisher_id INTEGER REFERENCES publisher (id) ON DELETE SET NULL,
    author_id INTEGER REFERENCES author(id)
);

-- Create user and credit card because of their close relationship
CREATE TABLE "user"
(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    address TEXT
);

CREATE TABLE credit_card
(
    id SERIAL PRIMARY KEY,
    card_holder VARCHAR(255) NOT NULL,
    number VARCHAR(16) UNIQUE NOT NULL,
    cvv VARCHAR(3)   NOT NULL,
    zip VARCHAR(20)  NOT NULL,
    user_id INTEGER REFERENCES "user" (id) ON DELETE CASCADE
);

-- Create shopping cart relation table
CREATE TABLE shopping_cart
(
    user_id INTEGER REFERENCES "user" (id) ON DELETE CASCADE,
    book_id VARCHAR(13) REFERENCES book (isbn) ON DELETE CASCADE,
    PRIMARY KEY (user_id, book_id)
);

-- Create rating and commenting together because they are closely related features
CREATE TABLE rating
(
    user_id INTEGER REFERENCES "user" (id) ON DELETE CASCADE,
    book_id VARCHAR(13) REFERENCES book (isbn) ON DELETE CASCADE,
    PRIMARY KEY (user_id, book_id),
    rating INTEGER CHECK (rating >= 1 AND rating <= 5) NOT NULL,
    datestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comment
(
    id SERIAL PRIMARY KEY,
    comment TEXT NOT NULL,
    datestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INTEGER REFERENCES "user" (id) ON DELETE CASCADE,
    book_id VARCHAR(13) REFERENCES book (isbn) ON DELETE CASCADE
);

-- Create wish_list related tables
CREATE TABLE wishlist
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL, -- may or may not have to be unique for entire user base --
    user_id INTEGER REFERENCES "user" (id) ON DELETE CASCADE
);

CREATE TABLE wishlist_book
(
    wish_list_id INTEGER REFERENCES wishlist (id) ON DELETE CASCADE,
    book_id VARCHAR(13) REFERENCES book (isbn) ON DELETE CASCADE,
    PRIMARY KEY (wish_list_id, book_id)
);
