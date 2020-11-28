CREATE TABLE reports
(
    uuid        uuid PRIMARY KEY,
    created_at  TIMESTAMP     NOT NULL,
    description TEXT          NOT NULL,
    image       bytea         NOT NULL,

    status      TEXT      NOT NULL,
    boar_status TEXT      NOT NULL,

    location_id bigint references locations
);

CREATE INDEX reports_uuid ON reports (uuid);
CREATE INDEX reports_created_at ON reports (created_at);
CREATE INDEX reports_status ON reports (status);
CREATE INDEX reports_boar_status ON reports (boar_status);
