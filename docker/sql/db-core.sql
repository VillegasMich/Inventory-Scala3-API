CREATE TABLE Item (
    id SERIAL,
    name varchar(255) NOT NULL,
    price int NOT NULL,
    location varchar(255) NOT NULL,
    PRIMARY KEY (id)
);