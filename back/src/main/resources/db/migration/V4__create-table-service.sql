CREATE TABLE "services" (
    "id" bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name" text NOT NULL,
    "status" Status NOT NULL DEFAULT 'AGUARDANDO PAGAMENTO',
    "price" numeric NOT NULL,
    "due" integer,
    "deliver_date" date,
    category_id BIGINT NOT NULL,
    CONSTRAINT fk_categoria FOREIGN KEY (category_id) REFERENCES category(id)
);
