CREATE TABLE reports
(
    uuid        uuid PRIMARY KEY,
    created_at  TIMESTAMP     NOT NULL,
    description TEXT          NOT NULL,
    image_url   TEXT          NOT NULL,

    status      TEXT NOT NULL,

    location_id bigint references locations
);
