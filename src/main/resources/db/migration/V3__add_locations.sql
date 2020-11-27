CREATE TABLE locations
(
    id        bigint PRIMARY KEY,
    latitude  numeric(24, 16) NOT NULL,
    longitude numeric(24, 16) NOT NULL
);

CREATE INDEX latitude_index ON locations (latitude);
CREATE INDEX longitude_index ON locations (longitude);