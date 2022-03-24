-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data

DELETE FROM owner where id > 1000000;
DELETE FROM horse where id > 1000000;

INSERT INTO owner (id, firstname, surname, email)
VALUES (1000001, 'Vittorio',    'Pelagia',  NULL),
       (1000002, 'Patrik',      'Solomiya', 'patrik@solomiya.xyz'),
       (1000003, 'Laure',       'Akamu',    NULL),
       (1000004, 'Felina',      'Nirmala',  'felinanirmala@xyz.zyx');

INSERT INTO horse (id, name, description, birthdate, gender, ownerId, damId, sireId)
VALUES (1000001, 'Trinity',     null,                       '2000-01-01', 'FEMALE', 1000004, NULL, NULL),
       (1000002, 'Solaris',     null,                       '2018-01-01', 'FEMALE', NULL,    NULL, NULL),
       (1000003, 'Fastbolt',    'Fastbolt Description',     '2018-01-01', 'MALE',   NULL,    NULL, NULL),
       (1000004, 'Cherish',     null,                       '2019-01-01', 'FEMALE', NULL,    1000002, 1000003),
       (1000005, 'Marley',      null,                       '2019-01-01', 'MALE',   1000003, NULL, NULL),
       (1000006, 'Bluebell',    null,                       '2019-01-01', 'FEMALE', 1000002, NULL, NULL),
       (1000007, 'Flashfire',   'Flashfire Description',    '2019-01-01', 'MALE',   1000001, NULL, NULL),
       (1000008, 'Passion',     null,                       '2020-01-01', 'MALE',   NULL,    1000006, 1000007),
       (1000009, 'Kizi',        null,                       '2020-01-01', 'FEMALE', NULL,    1000004, 1000005),
       (1000010, 'Morningfeet', 'Morningfeet Description',  '2021-01-01', 'MALE',   1000001, 1000009, 1000008);


