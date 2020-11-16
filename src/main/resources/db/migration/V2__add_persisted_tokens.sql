CREATE TABLE oauth_refresh_token (
    token_id character varying(255) NOT NULL,
    token bytea NOT NULL,
    authentication bytea NOT NULL,
    user_name character varying(255),
    expiration bigint NOT NULL
);
