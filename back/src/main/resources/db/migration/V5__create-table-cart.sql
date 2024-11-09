CREATE TABLE "carts" (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    service_id BIGINT REFERENCES services(id)
);