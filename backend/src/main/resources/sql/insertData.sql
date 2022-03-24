-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data

DELETE FROM horse where id > 1000000;
DELETE FROM owner where id > 1000000;

INSERT INTO owner (id, firstname, surname, email)
VALUES (1000001, 'Cara', 'Reese', 'carareese@mail.com'),
       (1000002, 'Antony', 'Burch', NULL),
       (1000003, 'Sofie', 'Prince', 'sofie-prince@xyz.com');

INSERT INTO horse (id, name, description, birthdate, gender)
VALUES (1000004, 'Bella', 'description 1', '2000-01-31', 'FEMALE'),
       (1000005, 'Alex', 'description 2', '1998-11-15', 'MALE'),
       (1000006, 'Lilly', 'description 3', '2001-02-03', 'FEMALE');

INSERT INTO horse(id, name, description, birthdate, gender, damId, sireId)
VALUES (1000007, 'Max', 'description 4', '2015-06-06', 'MALE', 1000004, 1000005);

INSERT INTO horse(id, name, description, birthdate, gender, ownerId, damId, sireId)
VALUES (1000008, 'Albert', 'description 5', '2016-06-06', 'MALE', 1000001, 1000004, 1000005);

