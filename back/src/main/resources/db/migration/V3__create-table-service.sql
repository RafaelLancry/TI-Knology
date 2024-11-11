CREATE TABLE service (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    status ENUM('ANALISE', 'PRODUZINDO', 'PAUSADO', 'CANCELADO', 'CONCLUIDO') NOT NULL DEFAULT 'ANALISE',
    price DECIMAL(10, 2) NOT NULL,
    due INT,
    deliver_date DATE,
    category_id BIGINT NOT NULL,
    CONSTRAINT fk_categoria FOREIGN KEY (category_id) REFERENCES category(id)
);