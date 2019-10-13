DROP TABLE IF EXISTS planes CASCADE;

CREATE TABLE planes(
                       id SERIAL PRIMARY KEY,
                       company_name text not null,
                       departure_place text not null,
                       landing_place text not null,
                       departure_time_in_millis BIGINT not null ,
                       landing_time_in_millis BIGINT not null,
                       delay int
);

INSERT into planes(COMPANY_NAME, DEPARTURE_PLACE, LANDING_PLACE, DEPARTURE_TIME_IN_MILLIS, LANDING_TIME_IN_MILLIS, delay)
VALUES ('UNITED', 'From here', 'To there', 0, 1, 0);
VALUES ('Emirates', 'From here', 'To there', 0, 1, 0);
VALUES ('Qantas', 'From here', 'To there', 0, 1, 0);
