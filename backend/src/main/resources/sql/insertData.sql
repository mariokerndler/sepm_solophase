-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data

DELETE FROM horse where id < 0;

INSERT INTO horse (id, name, description, birthdate, gender)
VALUES (-1, 'Bella', 'description 1', '2000-01-31', 'FEMALE'),
       (-2, 'Alex', 'description 2', '1998-11-15', 'MALE'),
       (-3, 'Lilly', 'description 3', '2001-02-03', 'FEMALE');

