-- Add more data so there are multiple ratings and comments for each book

-- Comments
INSERT INTO comment ("id", "comment", "datestamp", "user_id", "book_id")
VALUES ('11',
        'A superb work. Bravo!',
        CURRENT_TIMESTAMP,
        14,
        16),
       ('12',
        'A masterpiece with unforgettable characters and a rich world.',
        CURRENT_TIMESTAMP,
        13,
        16),
       ('13',
        'An unsettling and thought-provoking novel.',
        CURRENT_TIMESTAMP,
        12,
        16),
       ('14',
        'A great masterpiece! Fantastic! It is a must read.',
        CURRENT_TIMESTAMP,
        11, 16),
       ('15',
        'What a novel! You must buy this. Really u should.',
        CURRENT_TIMESTAMP,
        10, 16);

-- Ratings
INSERT INTO rating ("user_id", "book_id", "rating", "datestamp")
VALUES ('1', '4', 3, CURRENT_TIMESTAMP),
       ('1', '5', 4, CURRENT_TIMESTAMP),
       ('1', '6', 3, CURRENT_TIMESTAMP),
       ('2', '4', 2, CURRENT_TIMESTAMP),
       ('2', '5', 5, CURRENT_TIMESTAMP),
       ('2', '6', 5, CURRENT_TIMESTAMP),
       ('3', '4', 4, CURRENT_TIMESTAMP),
       ('3', '5', 1, CURRENT_TIMESTAMP),
       ('3', '6', 4, CURRENT_TIMESTAMP);
