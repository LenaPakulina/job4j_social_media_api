CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    registered TIMESTAMP            NOT NULL,
    name VARCHAR(128)               NOT NULL,
    email VARCHAR(128)       UNIQUE NOT NULL,
    password VARCHAR(128)           NOT NULL,
    user_zone VARCHAR(128)
);