CREATE TABLE post_update_history (
    id SERIAL PRIMARY KEY,
    created TIMESTAMP   NOT NULL,
    post_id INT         NOT NULL REFERENCES posts(id)
);