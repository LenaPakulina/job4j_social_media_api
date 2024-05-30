CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    created TIMESTAMP       NOT NULL,
    title VARCHAR(128)      NOT NULL,
    description TEXT        NOT NULL,
    user_id INT             NOT NULL REFERENCES users(id)
);