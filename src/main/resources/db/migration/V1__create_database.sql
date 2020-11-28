CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE roles
(
    uuid uuid PRIMARY KEY,
    name character varying(255) NOT NULL
);

CREATE TABLE users
(
    uuid     uuid PRIMARY KEY,
    enabled  boolean                NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);

CREATE TABLE user_role
(
    user_uuid uuid NOT NULL references users,
    role_uuid uuid NOT NULL references roles
);

CREATE INDEX users_uuid ON users (uuid);
CREATE UNIQUE INDEX users_usernames ON users (username);
CREATE INDEX users_enabled ON users (enabled);
