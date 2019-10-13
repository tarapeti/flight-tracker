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
