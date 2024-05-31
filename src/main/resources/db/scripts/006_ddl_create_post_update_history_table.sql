CREATE TABLE post_update_history (
    id SERIAL PRIMARY KEY,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    post_id INT                         NOT NULL REFERENCES posts(id)
);