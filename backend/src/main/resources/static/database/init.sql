-- MODELS
CREATE TABLE IF NOT EXISTS apprentice
(
    apprenticeId BIGINT PRIMARY KEY,
    username     VARCHAR(100) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE
    );


CREATE TABLE IF NOT EXISTS language
(
    languageId            BIGINT PRIMARY KEY,
    name                  VARCHAR(100) NOT NULL UNIQUE,
    additionalInformation TEXT
    );

CREATE TABLE IF NOT EXISTS apprentice_language
(
    apprenticeId BIGINT,
    languageId   BIGINT,
    PRIMARY KEY (apprenticeId, languageId),
    FOREIGN KEY (apprenticeId) REFERENCES apprentice (apprenticeId) ON DELETE CASCADE,
    FOREIGN KEY (languageId) REFERENCES language (languageId) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS question
(
    questionId        BIGINT PRIMARY KEY,
    questionContent   VARCHAR(100) NOT NULL UNIQUE,
    possibleResponses TEXT[],
    chosenResponse    VARCHAR(50)  NOT NULL
    );



CREATE TABLE IF NOT EXISTS exercise
(
    exerciseId   BIGINT PRIMARY KEY,
    title        VARCHAR(100) NOT NULL UNIQUE,
    pagination   INTEGER[],
    apprenticeId BIGINT       NOT NULL
    );

CREATE TABLE IF NOT EXISTS quiz
(
    exerciseId BIGINT PRIMARY KEY,
    rules      TEXT[],
    FOREIGN KEY (exerciseId) references exercise (exerciseId) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS flashcard
(
    exerciseId BIGINT PRIMARY KEY,
    FOREIGN KEY (exerciseId) references exercise (exerciseId) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS knowledge_test
(
    exerciseId BIGINT PRIMARY KEY,
    FOREIGN KEY (exerciseId) references exercise (exerciseId) ON DELETE CASCADE
    );



