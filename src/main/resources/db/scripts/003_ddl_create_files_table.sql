CREATE TABLE files (
    id SERIAL PRIMARY KEY,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    name VARCHAR(128)                   NOT NULL,
    path VARCHAR(256)                   NOT NULL,
    post_id INT                         NOT NULL REFERENCES posts(id)
);