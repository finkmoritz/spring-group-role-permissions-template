INSERT INTO "user" (id, username, password)
VALUES (0, 'user', '$2a$10$lVuoLRWY7sBe6FngnV2DAuIWFkfdukgerjGioApDNO2BLbQacnDaO'); -- password = 'password'

INSERT INTO "group" (id, name)
VALUES (0, 'group');

INSERT INTO group_user_role (id, group_id, user_id, role_id)
VALUES (0, 0, 0, 0);
