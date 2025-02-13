-- Publishers
INSERT INTO publisher ("id", "name", "discount")
VALUES ('6', 'Bloomsbury Publishing', '11.50'),
       ('7', 'Scholastic Inc.', '13.25'),
       ('8', 'Oxford University Press', '9.00'),
       ('9', 'Crown Publishing Group', '14.75'),
       ('10', 'Vintage Books', '10.00');

-- Authors
INSERT INTO author ("id", "first_name", "last_name", "biography", "publisher_id")
VALUES ('11', 'Suzanne', 'Collins', 'American author known for The Hunger Games series.', '7'),
       ('12', 'Neil', 'Gaiman', 'British author of fantasy novels like American Gods and Coraline.', '6'),
       ('13', 'Margaret', 'Atwood', 'Canadian writer known for The Handmaid’s Tale.', '9'),
       ('14', 'Terry', 'Pratchett', 'British author famous for the Discworld series.', '8'),
       ('15', 'Dan', 'Brown', 'American author of thriller novels like The Da Vinci Code.', '10');

-- Genres
INSERT INTO genre ("id", "name")
VALUES ('6', 'Thriller'),
       ('7', 'Adventure'),
       ('8', 'Dystopian'),
       ('9', 'Humor'),
       ('10', 'Non-Fiction');

-- Books
INSERT INTO book ("id", "isbn", "title", "description", "year", "price", "copies_sold", "genre_id", "publisher_id",
                  "author_id")
VALUES ('11', '9780439023481',
        'The Hunger Games',
        'A dystopian novel about a televised fight to the death in a totalitarian society.',
        2008, 18.99, 23000000, 8, 7, 11),
       ('12', '9780060558123',
        'American Gods',
        'A fantasy novel exploring the conflict between old gods and new in modern America.',
        2001, 15.99, 1200000, 7, 6, 12),
       ('13', '9780385490818',
        'The Handmaid’s Tale',
        'A dystopian novel about a totalitarian society that subjugates women.',
        1985, 14.50, 8000000, 8, 9, 13),
       ('14', '9780062225672',
        'Good Omens',
        'A humorous novel about an angel and demon teaming up to stop the apocalypse.',
        1990, 16.75, 2500000, 9, 6, 12),
       ('15', '9780552150736',
        'The Colour of Magic',
        'The first book in the Discworld series, a comedic fantasy world.',
        1983, 12.99, 5000000, 9, 8, 14),
       ('16', '9780385504201',
        'The Da Vinci Code',
        'A thriller novel about a symbologist uncovering secrets hidden in famous artworks.',
        2003, 19.99, 8000000, 6, 10, 15);

-- Users
INSERT INTO "user" ("id", "username", "password", "first_name", "last_name", "email", "address")
VALUES ('11', 'hungergamesfan', 'hashedpassword11', 'Kelly', 'Adams', 'kelly.adams@example.com',
        '111 Maple St, Chicago, IL'),
       ('12', 'fantasylife', 'hashedpassword12', 'Liam', 'Baker', 'liam.baker@example.com', '222 Oak Ave, Houston, TX'),
       ('13', 'dystopiaaddict', 'hashedpassword13', 'Mia', 'Carter', 'mia.carter@example.com',
        '333 Pine Rd, Orlando, FL'),
       ('14', 'humorlover', 'hashedpassword14', 'Noah', 'Davis', 'noah.davis@example.com', '444 Birch Ln, Atlanta, GA'),
       ('15', 'thrillerchaser', 'hashedpassword15', 'Olivia', 'Evans', 'olivia.evans@example.com',
        '555 Cedar Blvd, Denver, CO');

-- Credit Cards
INSERT INTO credit_card ("id", "card_holder", "number", "cvv", "zip", "user_id")
VALUES ('6', 'Kelly Adams', '4111222233334444', '111', '60601', '11'),
       ('7', 'Liam Baker', '5500111122223333', '222', '77001', '12'),
       ('8', 'Mia Carter', '3400111122223333', '333', '32801', '13'),
       ('9', 'Noah Davis', '6011222233334444', '444', '30301', '14'),
       ('10', 'Olivia Evans', '3530111122223333', '555', '80202', '15');

-- Shopping Cart
INSERT INTO shopping_cart ("user_id", "book_id")
VALUES ('11', '11'),
       ('12', '12'),
       ('13', '13'),
       ('14', '14'),
       ('15', '15');

-- Ratings
INSERT INTO rating ("user_id", "book_id", "rating", "datestamp")
VALUES ('11', '11', 5, CURRENT_TIMESTAMP),
       ('12', '12', 4, CURRENT_TIMESTAMP),
       ('13', '13', 5, CURRENT_TIMESTAMP),
       ('14', '14', 4, CURRENT_TIMESTAMP),
       ('15', '15', 5, CURRENT_TIMESTAMP);

-- Comments
INSERT INTO comment ("id", "comment", "datestamp", "user_id", "book_id")
VALUES ('6',
        '"A gripping and intense story that keeps you on the edge of your seat."',
        CURRENT_TIMESTAMP,
        11,
        11),
       ('7',
        '"A masterpiece of fantasy with unforgettable characters and a rich world."',
        CURRENT_TIMESTAMP,
        12,
        12),
       ('8',
        '"An unsettling and thought-provoking dystopia that resonates deeply."',
        CURRENT_TIMESTAMP,
        13,
        13),
       ('9',
        '"Hilarious and clever! A must-read for fans of comedic fantasy."',
        CURRENT_TIMESTAMP,
        14, 15),
       ('10',
        '"An exhilarating thriller with twists and turns you won’t see coming."',
        CURRENT_TIMESTAMP,
        15, 16);

-- Wishlists
INSERT INTO wishlist ("id", "name", "user_id")
VALUES ('6', 'Kelly''s Dystopian Picks', '11'),
       ('7', 'Liam''s Fantasy Favorites', '12'),
       ('8', 'Mia''s Must-Read Dystopias', '13'),
       ('9', 'Noah''s Humor Collection', '14'),
       ('10', 'Olivia''s Thrilling Reads', '15');

-- Wishlist Books
INSERT INTO wishlist_book ("wish_list_id", "book_id")
VALUES (6, '11'),
       (7, '12'),
       (8, '13'),
       (9, '14'),
       (10, '15');

