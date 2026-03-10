-- =====================================================
-- Drop tables in reverse dependency order
-- =====================================================
DROP TABLE IF EXISTS T_MAILS;
DROP TABLE IF EXISTS T_ADDRESSES;
DROP TABLE IF EXISTS T_PEOPLE;

-- =====================================================
-- Table: T_PEOPLE
-- =====================================================
CREATE TABLE T_PEOPLE (
                          ID          BIGSERIAL PRIMARY KEY,          -- maps to java.lang.Long
                          FULL_NAME   VARCHAR(90) NOT NULL,           -- @Column(length = 90)
                          PIN         VARCHAR(255)                     -- default length
);

-- =====================================================
-- Table: T_ADDRESSES
-- =====================================================
CREATE TABLE T_ADDRESSES (
                             ID           BIGSERIAL PRIMARY KEY,          -- maps to java.lang.Long
                             T_PEOPLE_ID  BIGINT NOT NULL,                -- foreign key to PEOPLE.ID
                             ADDR_TYPE    VARCHAR(5) NOT NULL,            -- @Column(length = 5)
                             ADDR_INFO    VARCHAR(300),                    -- @Column(length = 300)

                             CONSTRAINT fk_addresses_people
                                 FOREIGN KEY (T_PEOPLE_ID)
                                     REFERENCES T_PEOPLE (ID)
                                     ON DELETE CASCADE                          -- matches orphanRemoval + cascade ALL
);

-- Index on foreign key for better join performance
CREATE INDEX idx_addresses_people ON T_ADDRESSES (T_PEOPLE_ID);

-- =====================================================
-- Table: T_MAILS
-- =====================================================
CREATE TABLE T_MAILS (
                         ID           BIGSERIAL PRIMARY KEY,          -- maps to java.lang.Long
                         T_PEOPLE_ID  BIGINT NOT NULL,                -- foreign key to PEOPLE.ID
                         EMAIL_TYPE   VARCHAR(255) NOT NULL,           -- default length
                         EMAIL        VARCHAR(40),                      -- @Column(length = 40)

                         CONSTRAINT fk_mails_people
                             FOREIGN KEY (T_PEOPLE_ID)
                                 REFERENCES T_PEOPLE (ID)
                                 ON DELETE CASCADE                          -- matches orphanRemoval + cascade ALL
);

-- Index on foreign key for better join performance
CREATE INDEX idx_mails_people ON T_MAILS (T_PEOPLE_ID);




-- Insert 10 people
INSERT INTO T_PEOPLE (FULL_NAME, PIN) VALUES
                                          ('John Doe', '1234567890'),
                                          ('Jane Smith', '2345678901'),
                                          ('Alice Johnson', '3456789012'),
                                          ('Bob Williams', '4567890123'),
                                          ('Charlie Brown', '5678901234'),
                                          ('Diana Prince', '6789012345'),
                                          ('Edward Norton', '7890123456'),
                                          ('Fiona Apple', '8901234567'),
                                          ('George Michael', '9012345678'),
                                          ('Hannah Montana', '0123456789');

-- Insert addresses for each person (varying counts)
-- Person 1: John Doe (ID = 1)
INSERT INTO T_ADDRESSES (T_PEOPLE_ID, ADDR_TYPE, ADDR_INFO) VALUES
                                                                (1, 'HOME', '123 Main St, Springfield, IL'),
                                                                (1, 'WORK', '456 Office Park, Suite 100, Springfield, IL');

-- Person 2: Jane Smith (ID = 2)
INSERT INTO T_ADDRESSES (T_PEOPLE_ID, ADDR_TYPE, ADDR_INFO) VALUES
                                                                (2, 'HOME', '789 Elm St, Shelbyville, IN'),
                                                                (2, 'WORK', '321 Business Ave, Shelbyville, IN'),
                                                                (2, 'OTHER', 'PO Box 123, Shelbyville, IN');

-- Person 3: Alice Johnson (ID = 3)
INSERT INTO T_ADDRESSES (T_PEOPLE_ID, ADDR_TYPE, ADDR_INFO) VALUES
                                                                (3, 'HOME', '12 Oak Dr, Ogdenville, OH'),
                                                                (3, 'WORK', '34 Corporate Ln, Ogdenville, OH');

-- Person 4: Bob Williams (ID = 4)
INSERT INTO T_ADDRESSES (T_PEOPLE_ID, ADDR_TYPE, ADDR_INFO) VALUES
                                                                (4, 'HOME', '56 Maple Ave, North Haverbrook, IA'),
                                                                (4, 'WORK', '78 Industrial Pkwy, North Haverbrook, IA');

-- Person 5: Charlie Brown (ID = 5)
INSERT INTO T_ADDRESSES (T_PEOPLE_ID, ADDR_TYPE, ADDR_INFO) VALUES
    (5, 'HOME', '90 Peanuts Ln, Mayfield, KS');

-- Person 6: Diana Prince (ID = 6)
INSERT INTO T_ADDRESSES (T_PEOPLE_ID, ADDR_TYPE, ADDR_INFO) VALUES
                                                                (6, 'HOME', '123 Themyscira Way, Paradise Island, FL'),
                                                                (6, 'WORK', '456 Justice League HQ, Metropolis, NY'),
                                                                (6, 'SECT', 'P.O. Box 789, Gateway City, CA');

-- Person 7: Edward Norton (ID = 7)
INSERT INTO T_ADDRESSES (T_PEOPLE_ID, ADDR_TYPE, ADDR_INFO) VALUES
                                                                (7, 'HOME', '101 Fight Club St, Wilmington, DE'),
                                                                (7, 'WORK', '202 Paper St, New York, NY');

-- Person 8: Fiona Apple (ID = 8)
INSERT INTO T_ADDRESSES (T_PEOPLE_ID, ADDR_TYPE, ADDR_INFO) VALUES
    (8, 'HOME', '303 Tidal Ln, New York, NY');

-- Person 9: George Michael (ID = 9)
INSERT INTO T_ADDRESSES (T_PEOPLE_ID, ADDR_TYPE, ADDR_INFO) VALUES
                                                                (9, 'HOME', '505 Careless Whisper Ave, London, KY'),
                                                                (9, 'WORK', '606 Faith Blvd, London, KY'),
                                                                (9, 'STO', '707 Record Row, London, KY');

-- Person 10: Hannah Montana (ID = 10)
INSERT INTO T_ADDRESSES (T_PEOPLE_ID, ADDR_TYPE, ADDR_INFO) VALUES
                                                                (10, 'HOME', '808 Best of Both Worlds St, Nashville, TN'),
                                                                (10, 'WORK', '909 Hollywood Blvd, Los Angeles, CA');

-- Insert emails for each person
-- Person 1: John Doe
INSERT INTO T_MAILS (T_PEOPLE_ID, EMAIL_TYPE, EMAIL) VALUES
                                                         (1, 'PERSONAL', 'john.doe@email.com'),
                                                         (1, 'WORK', 'jdoe@company.com');

-- Person 2: Jane Smith
INSERT INTO T_MAILS (T_PEOPLE_ID, EMAIL_TYPE, EMAIL) VALUES
                                                         (2, 'PERSONAL', 'jane.smith@email.com'),
                                                         (2, 'WORK', 'jsmith@business.com'),
                                                         (2, 'NEWSLETTER', 'jane@news.com');

-- Person 3: Alice Johnson
INSERT INTO T_MAILS (T_PEOPLE_ID, EMAIL_TYPE, EMAIL) VALUES
                                                         (3, 'PERSONAL', 'alice.j@email.com'),
                                                         (3, 'WORK', 'ajohnson@tech.com');

-- Person 4: Bob Williams
INSERT INTO T_MAILS (T_PEOPLE_ID, EMAIL_TYPE, EMAIL) VALUES
                                                         (4, 'PERSONAL', 'bob.w@email.com'),
                                                         (4, 'WORK', 'bwilliams@industry.com');

-- Person 5: Charlie Brown
INSERT INTO T_MAILS (T_PEOPLE_ID, EMAIL_TYPE, EMAIL) VALUES
    (5, 'PERSONAL', 'charlie.brown@email.com');

-- Person 6: Diana Prince
INSERT INTO T_MAILS (T_PEOPLE_ID, EMAIL_TYPE, EMAIL) VALUES
                                                         (6, 'PERSONAL', 'diana.prince@email.com'),
                                                         (6, 'WORK', 'wonderwoman@jl.com'),
                                                         (6, 'SECRET', 'princess@themyscira.gov');

-- Person 7: Edward Norton
INSERT INTO T_MAILS (T_PEOPLE_ID, EMAIL_TYPE, EMAIL) VALUES
                                                         (7, 'PERSONAL', 'ed.norton@email.com'),
                                                         (7, 'WORK', 'enorton@actor.com');

-- Person 8: Fiona Apple
INSERT INTO T_MAILS (T_PEOPLE_ID, EMAIL_TYPE, EMAIL) VALUES
                                                         (8, 'PERSONAL', 'fiona.apple@email.com'),
                                                         (8, 'FAN', 'fiona@music.com');

-- Person 9: George Michael
INSERT INTO T_MAILS (T_PEOPLE_ID, EMAIL_TYPE, EMAIL) VALUES
                                                         (9, 'PERSONAL', 'george.michael@email.com'),
                                                         (9, 'FAN', 'gm@wham.com'),
                                                         (9, 'WORK', 'george@recordlabel.com');

-- Person 10: Hannah Montana
INSERT INTO T_MAILS (T_PEOPLE_ID, EMAIL_TYPE, EMAIL) VALUES
                                                         (10, 'PERSONAL', 'hannah.montana@email.com'),
                                                         (10, 'FAN', 'miley@disney.com');