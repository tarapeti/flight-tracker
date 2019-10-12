DROP TABLE IF EXISTS planes CASCADE;

CREATE TABLE planes(
                       id SERIAL PRIMARY KEY,
                       company_name text not null,
                       departure_place text not null,
                       landing_place text not null,
                       departure_time_in_millis BIGINT not null ,
                       landing_time_in_millis BIGINT not null,
                       late_by_mins_in_millis BIGINT
);

INSERT into planes(COMPANY_NAME, DEPARTURE_PLACE, LANDING_PLACE, DEPARTURE_TIME_IN_MILLIS, LANDING_TIME_IN_MILLIS, LATE_BY_MINS_IN_MILLIS)
VALUES ('QUATAR', 'From here', 'To there', 0, 1, 0);
