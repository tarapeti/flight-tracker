DROP TABLE IF EXISTS plane CASCADE;

CREATE TABLE planes(
                       id SERIAL PRIMARY KEY,
                       companyName text not null,
                       departurePlace text not null,
                       landingPlace text not null,
                       departureTimeInMillis BIGINT not null ,
                       landingTimeInMillis BIGINT not null,
                       lateByMinsInMillis BIGINT
);

INSERT INTO planes(companyName, departurePlace, landingPlace, departureTimeInMillis, landingTimeInMillis, lateByMinsInMillis)
VALUES ('QUATAR', 'Miskolc', 'Budapest', 0, 1, 0);