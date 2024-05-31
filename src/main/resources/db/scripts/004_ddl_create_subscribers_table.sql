CREATE TABLE subscribers (
    id SERIAL PRIMARY KEY,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    from_user_id INT                    NOT NULL REFERENCES users(id),
    to_user_id INT                      NOT NULL REFERENCES users(id),
    UNIQUE(from_user_id, to_user_id)
);