DROP TABLE IF EXISTS plane CASCADE;

CREATE TABLE planes(
                       id SERIAL PRIMARY KEY,
                       companyName text not null,
                       departurePlace text not null,
                       landingPlace text not null,
                       departureTimeInMilis BIGINT not null ,
                       landingTimeInMilis BIGINT not null,
                       lateByMinsInMilis BIGINT
);

INSERT INTO planes(companyName, departurePlace, landingPlace, departureTimeInMilis, landingTimeInMilis, lateByMinsInMilis)
VALUES ('QUATAR', 'Miskolc', 'Budapest', 0, 1, 0);