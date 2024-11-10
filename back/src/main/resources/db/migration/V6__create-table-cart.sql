CREATE TABLE "purchase" (
    cart_id BIGINT REFERENCES cart(id) ON DELETE CASCADE,
    purchase_id BIGINT,
    PRIMARY KEY (cart_id, purchase_id)
);