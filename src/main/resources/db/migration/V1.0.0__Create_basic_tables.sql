CREATE TABLE "user" (
    id INTEGER NOT NULL PRIMARY KEY,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);
CREATE SEQUENCE user_seq start with 1 increment by 50;

CREATE TABLE "group" (
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL
);
CREATE SEQUENCE group_seq start with 1 increment by 50;

CREATE TABLE role (
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL
);
CREATE SEQUENCE role_seq start with 1 increment by 50;

CREATE TABLE permission (
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL
);
CREATE SEQUENCE permission_seq start with 1 increment by 50;

CREATE TABLE role_permission (
    id INTEGER NOT NULL PRIMARY KEY,
    role_id INTEGER NOT NULL REFERENCES role ON DELETE CASCADE,
    permission_id INTEGER NOT NULL REFERENCES permission ON DELETE CASCADE
);
CREATE SEQUENCE role_permission_seq start with 1 increment by 50;

CREATE TABLE group_user_role (
    id INTEGER NOT NULL PRIMARY KEY,
    group_id INTEGER NOT NULL REFERENCES "group" ON DELETE CASCADE,
    user_id INTEGER NOT NULL REFERENCES "user" ON DELETE CASCADE,
    role_id INTEGER NOT NULL REFERENCES role ON DELETE CASCADE
);
CREATE SEQUENCE group_user_role_seq start with 1 increment by 50;
