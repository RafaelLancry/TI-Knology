CREATE TABLE "users" (
    "id" bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name" text NOT NULL,
    "password" text NOT NULL,
    "email" text UNIQUE NOT NULL,
    "cpf" text UNIQUE NOT NULL,
    "birthdate" date,
    "phone" text UNIQUE,
    "cart_id" bigint
);