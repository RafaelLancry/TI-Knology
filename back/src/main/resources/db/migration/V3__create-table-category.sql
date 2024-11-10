CREATE TABLE "category" (
    "id" bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name" text UNIQUE NOT NULL,
    "description" text
);