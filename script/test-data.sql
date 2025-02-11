-- Use this SQL script to load the database with dummy data for development testing

INSERT INTO publisher ("id", "name", "discount")
VALUES ('1', 'Penguin Random House', '10.50'),
       ('2', 'HarperCollins', '12.00'),
       ('3', 'Simon & Schuster', '8.75'),
       ('4', 'Macmillan Publishers', '15.20'),
       ('5', 'Hachette Book Group', '9.90');

INSERT INTO author ("id", "first_name", "last_name", "biography", "publisher_id")
VALUES ('1', 'John', 'Steinbeck', 'American author known for Grapes of Wrath and Of Mice and Men.', '1'),
       ('2', 'Agatha', 'Christie', 'British writer known for her detective novels.', '2'),
       ('3', 'George', 'Orwell', 'English novelist and essayist, best known for 1984 and Animal Farm.', '3'),
       ('4', 'Jane', 'Austen', 'English novelist known for Pride and Prejudice.', '4'),
       ('5', 'J.K.', 'Rowling', 'British author, best known for the Harry Potter series.', '5'),
       ('6', 'J.D.', 'Salinger', 'American writer known for The Catcher in the Rye.', '1'),
       ('7', 'F. Scott', 'Fitzgerald', 'American novelist famous for The Great Gatsby.', '2'),
       ('8', 'Frank', 'Herbert', 'American science-fiction author known for Dune.', '3'),
       ('9', 'Harper', 'Lee', 'American novelist known for To Kill a Mockingbird.', '4'),
       ('10', 'J.R.R.', 'Tolkien', 'British writer and professor, best known for The Hobbit and The Lord of the Rings.',
        '5');

INSERT INTO genre ("id", "name")
VALUES ('1', 'Fiction'),
       ('2', 'Mystery'),
       ('3', 'Science Fiction'),
       ('4', 'Fantasy'),
       ('5', 'Historical Fiction');

INSERT INTO book ("id", "isbn", "title", "description", "year", "price", "copies_sold", "genre_id", "publisher_id",
                  "author_id")
VALUES ('1', '9780143039433', 'The Grapes of Wrath',
        'A novel about the struggles of a poor family during the Great Depression.', '1939', '15.99', '5000000', '5',
        '1', '1'),
       ('2', '9780007119318', 'Murder on the Orient Express',
        'A detective novel featuring Hercule Poirot solving a murder on a train.', '1934', '12.50', '3000000', '2', '2',
        '2'),
       ('3', '9780451524935', '1984', 'A dystopian novel about a totalitarian regime and surveillance.', '1949',
        '10.99', '10000000', '3', '3', '3'),
       ('4', '9780141439518', 'Pride and Prejudice',
        'A classic romantic novel about the manners and matrimonial machinations of Regency England.', '1813', '9.99',
        '2000000', '1', '4', '4'),
       ('5', '9780439554930',
        'Harry Potter and the Sorcerer''s Stone', 'The first book in the Harry Potter series about a young wizard.',
        '1997', '20.00', '120000000', '4', '5', '5'),
       ('6', '9780316769488', 'The Catcher in the Rye', 'A novel about teenage alienation and rebellion.', '1951',
        '14.99', '6500000', '1', '1', '6'),
       ('7', '9780743273565', 'The Great Gatsby', 'A novel set in the Roaring Twenties about wealth and lost love.',
        '1925', '11.99', '25000000', '5', '2', '7'),
       ('8', '9780553293357', 'Dune',
        'A science fiction novel about a desert planet and a young noble’s rise to power.', '1965', '18.99', '2000000',
        '3', '3', '8'),
       ('9', '9780061120084', 'To Kill a Mockingbird', 'A novel about racial injustice in the Deep South.', '1960',
        '13.50', '4000000', '5', '4', '9'),
       ('10', '9780547928227', 'The Hobbit',
        'A fantasy novel about a hobbit’s journey to reclaim treasure guarded by a dragon.', '1937', '16.99',
        '140000000', '4', '5', '10');

INSERT INTO "user" ("id", "username", "password", "first_name", "last_name", "email", "address")
VALUES ('1', 'booklover99', 'hashedpassword1', 'Alice', 'Johnson', 'alice.johnson@example.com',
        '123 Maple St, Springfield, IL'),
       ('2', 'reader123', 'hashedpassword2', 'Bob', 'Smith', 'bob.smith@example.com', '456 Oak Ave, Lincoln, NE'),
       ('3', 'literaryfan', 'hashedpassword3', 'Charlie', 'Brown', 'charlie.brown@example.com',
        '789 Pine Rd, Denver, CO'),
       ('4', 'novelenthusiast', 'hashedpassword4', 'Diana', 'Miller', 'diana.miller@example.com',
        '321 Birch Ln, Austin, TX'),
       ('5', 'fictionaddict', 'hashedpassword5', 'Ethan', 'Davis', 'ethan.davis@example.com',
        '654 Cedar Blvd, Seattle, WA'),
       ('6', 'pageflipper', 'hashedpassword6', 'Fiona', 'Garcia', 'fiona.garcia@example.com', '987 Elm St, Miami, FL'),
       ('7', 'chaptermaster', 'hashedpassword7', 'George', 'Harrison', 'george.harrison@example.com',
        '246 Willow Dr, Phoenix, AZ'),
       ('8', 'hardcoverfan', 'hashedpassword8', 'Hannah', 'Lopez', 'hannah.lopez@example.com',
        '135 Spruce Ct, Nashville, TN'),
       ('9', 'bookwormz', 'hashedpassword9', 'Ian', 'Martinez', 'ian.martinez@example.com',
        '579 Redwood Way, Portland, OR'),
       ('10', 'fantasyreader', 'hashedpassword10', 'Julia', 'Clark', 'julia.clark@example.com',
        '802 Cypress Pl, Boston, MA');

INSERT INTO credit_card ("id", "card_holder", "number", "cvv", "zip", "user_id")
VALUES ('1', 'Alice Johnson', '4111111111111111', '123', '62704', '1'),
       ('2', 'Bob Smith', '5500000000000004', '456', '68510', '2'),
       ('3', 'Charlie Brown', '340000000000009', '789', '80203', '3'),
       ('4', 'Diana Miller', '6011000000000004', '321', '78701', '4'),
       ('5', 'Ethan Davis', '3530111333300000', '654', '98101', '5');

INSERT INTO shopping_cart ("user_id", "book_id")
VALUES ('1', '1'),
       ('2', '2'),
       ('3', '3'),
       ('4', '4'),
       ('5', '5');

INSERT INTO rating ("user_id", "book_id", "rating", "datestamp")
VALUES ('1', '1', '5', '2025-02-11 00:30:01.416597'),
       ('2', '2', '4', '2025-02-11 00:30:01.416597'),
       ('3', '3', '5', '2025-02-11 00:30:01.416597'),
       ('4', '4', '4', '2025-02-11 00:30:01.416597'),
       ('5', '5', '5', '2025-02-11 00:30:01.416597');

INSERT INTO comment ("id", "comment", "datestamp", "user_id", "book_id")
VALUES ('1', 'An emotional and powerful story of perseverance through hardship.', '2025-02-11 00:30:35.77498', '1',
        '1'),
       ('2', 'A thrilling mystery with a classic twist. Highly recommend it!', '2025-02-11 00:30:35.77498', '2', '2'),
       ('3', 'A thought-provoking and chilling dystopia that feels all too real.', '2025-02-11 00:30:35.77498', '3',
        '3'),
       ('4', 'A beautifully written romance. I couldn''t put it down!', '2025-02-11 00:30:35.77498', '4', '4'),
       ('5', 'A captivating and magical adventure for readers of all ages.', '2025-02-11 00:30:35.77498', '5', '5');

INSERT INTO wishlist ("id", "name", "user_id")
VALUES ('1', 'Alice''s Favorite Books', '1'),
       ('2', 'Bob''s Mystery Picks', '2'),
       ('3', 'Charlie''s Sci-Fi Wishlist', '3'),
       ('4', 'Diana''s Romance Reads', '4'),
       ('5', 'Ethan''s Fantasy Favorites', '5');

INSERT INTO wishlist_book ("wish_list_id", "book_id")
VALUES ('1', '1'),
       ('2', '2'),
       ('3', '3'),
       ('4', '4'),
       ('5', '5');