create table accounts (
    id SERIAL PRIMARY KEY,
    login VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL
);