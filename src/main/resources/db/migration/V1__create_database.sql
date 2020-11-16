CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE roles
(
    uuid    uuid NOT NULL,
    name    character varying(255),
    user_id uuid NOT NULL
);

CREATE TABLE users
(
    uuid     uuid                   NOT NULL,
    enabled  boolean,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);

CREATE TABLE user_role
(
    user_uuid uuid NOT NULL,
    role_uuid uuid NOT NULL
);

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (uuid);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (uuid);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_username UNIQUE (username);

ALTER TABLE ONLY user_role
    ADD CONSTRAINT fk_user_role_user FOREIGN KEY (user_uuid) REFERENCES users (uuid);

ALTER TABLE ONLY user_role
    ADD CONSTRAINT fk_user_role_role FOREIGN KEY (role_uuid) REFERENCES roles (uuid);
