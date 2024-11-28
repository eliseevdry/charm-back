CREATE TABLE profile(
    id BIGSERIAL PRIMARY KEY ,
    email VARCHAR NOT NULL UNIQUE ,
    password VARCHAR NOT NULL,
    "name" VARCHAR,
    surname VARCHAR,
    birth_date DATE,
    about TEXT,
    gender VARCHAR,
    photo VARCHAR,
    status VARCHAR NOT NULL DEFAULT 'INACTIVE',
    role VARCHAR NOT NULL DEFAULT 'USER'
); 

DROP TABLE profile;

ALTER TABLE profile ADD COLUMN created_date TIMESTAMP NOT NULL DEFAULT current_timestamp;

INSERT INTO profile (email, password, name, surname, birth_date, about, gender, status, role) VALUES 
('admin@charm.ru', 'qwerty', 'Admin', NULL, NULL, NULL, NULL, 'INACTIVE', 'ADMIN'),
('ivanov@mail.ru', '123', 'Ivan', 'Ivanov', '2001-12-03', 'I am QA', 'MALE', 'ACTIVE', 'USER'),
('sidorova@mail.ru', '456', 'Elena', 'Sidorova', '1999-09-01', 'I am Java Dev', 'FEMALE', 'ACTIVE', 'USER');

DELETE FROM profile WHERE email = 'admin@charm.ru';

SELECT * FROM profile;

CREATE TABLE "like"(
    from_profile BIGINT NOT NULL REFERENCES profile (id),
    to_profile BIGINT NOT NULL REFERENCES profile (id),
    "like" BOOLEAN NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (from_profile, to_profile)
);

INSERT INTO "like" (from_profile, to_profile, "like") VALUES 
(5, 6, TRUE), (6, 5, TRUE);

SELECT * FROM "like";