-- INSERTING DUMMY DATA
INSERT INTO apprentice (apprenticeId, username, password, email)
VALUES (1, 'user1', 'password1', 'user1@example.com'),
       (2, 'user2', 'password2', 'user2@example.com'),
       (3, 'user3', 'password3', 'user3@example.com'),
       (4, 'user4', 'password4', 'user4@example.com'),
       (5, 'user5', 'password5', 'user5@example.com');

INSERT INTO language (languageId, name, additionalInformation)
VALUES (1, 'English', 'Commonly spoken language'),
       (2, 'Spanish', 'Widely spoken in Latin America'),
       (3, 'French', 'Spoken in France and parts of Canada'),
       (4, 'German', 'Spoken in Germany and Austria'),
       (5, 'Japanese', 'Spoken in Japan');

INSERT INTO apprentice_language (apprenticeId, languageId)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (2, 3),
       (3, 4),
       (3, 1),
       (4, 5),
       (5, 3),
       (5, 4),
       (5, 2);

INSERT INTO question (questionId, questionContent, possibleResponses, chosenResponse)
VALUES (1, 'What is the capital of France?', '{"Paris", "Berlin", "Madrid", "Rome"}', 'Paris'),
       (2, 'What is 2 + 2?', '{"3", "4", "5", "6"}', '4'),
       (3, 'Which planet is known as the Red Planet?', '{"Earth", "Mars", "Jupiter", "Saturn"}', 'Mars'),
       (4, 'What is the largest ocean on Earth?', '{"Atlantic", "Indian", "Arctic", "Pacific"}', 'Pacific'),
       (5, 'Who wrote "Hamlet"?', '{"Shakespeare", "Dickens", "Tolkien", "Austen"}', 'Shakespeare');

INSERT INTO exercise (exerciseId, title, pagination, apprenticeId)
VALUES (1, 'Quiz 1', '{1, 2, 3}', 1),
       (2, 'Quiz 2', '{1, 2}', 2),
       (3, 'Flashcard 1', '{1, 2, 3, 4}', 3),
       (4, 'Knowledge Test 1', '{1, 2, 3, 4, 5}', 4),
       (5, 'Quiz 3', '{1}', 5);

INSERT INTO quiz (exerciseId, rules)
VALUES (1, '{"Rule 1", "Rule 2"}'),
       (2, '{"Rule 1"}'),
       (5, '{"Rule 1", "Rule 2", "Rule 3"}');

INSERT INTO flashcard (exerciseId) VALUES
    (3);

INSERT INTO knowledge_test (exerciseId) VALUES
    (4);