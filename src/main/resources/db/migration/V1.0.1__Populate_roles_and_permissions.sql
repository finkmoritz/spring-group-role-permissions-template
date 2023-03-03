INSERT INTO role
VALUES (0, 'ADMIN');

INSERT INTO role
VALUES (1, 'MEMBER');

INSERT INTO permission
VALUES (0, 'READ_GROUP');

INSERT INTO permission
VALUES (1, 'UPDATE_GROUP');

INSERT INTO permission
VALUES (2, 'DELETE_GROUP');

INSERT INTO role_permission
VALUES (0, 0, 0); -- ADMIN : READ_GROUP

INSERT INTO role_permission
VALUES (1, 0, 1); -- ADMIN : UPDATE_GROUP

INSERT INTO role_permission
VALUES (2, 0, 2); -- ADMIN : DELETE_GROUP

INSERT INTO role_permission
VALUES (3, 1, 0); -- MEMBER : READ_GROUP
