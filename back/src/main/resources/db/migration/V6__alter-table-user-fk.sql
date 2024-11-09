ALTER TABLE users
    ADD CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES carts(id);
