CREATE TYPE report_status AS ENUM ('new','pending','resolved','closed');

CREATE TABLE reports
(
    uuid        uuid PRIMARY KEY,
    created_at  TIMESTAMP     NOT NULL,
    description TEXT          NOT NULL,
    image_url   TEXT          NOT NULL,

    status      report_status NOT NULL,

    location_id bigint references locations
);
