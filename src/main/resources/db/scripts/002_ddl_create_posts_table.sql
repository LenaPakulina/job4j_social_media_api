CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    title VARCHAR(128)                  NOT NULL,
    description TEXT                    NOT NULL,
    user_id INT                         NOT NULL REFERENCES users(id)
);