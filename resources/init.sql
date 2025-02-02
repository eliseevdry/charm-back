CREATE TABLE profile(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR NOT NULL UNIQUE,  
    password VARCHAR NOT NULL, 
    "name" VARCHAR,
    surname VARCHAR,
    birth_date DATE,
    about TEXT,
    gender VARCHAR,
    photo VARCHAR,
    status VARCHAR NOT NULL DEFAULT 'INACTIVE',
    role VARCHAR NOT NULL DEFAULT 'USER',
    created_date TIMESTAMP NOT NULL DEFAULT current_timestamp
);

COMMENT ON COLUMN profile.id IS 'sortable';
COMMENT ON COLUMN profile.email IS 'sortable';
COMMENT ON COLUMN profile."name" IS 'sortable';
COMMENT ON COLUMN profile.surname IS 'sortable';
COMMENT ON COLUMN profile.birth_date IS 'sortable';
COMMENT ON COLUMN profile.gender IS 'sortable';
COMMENT ON COLUMN profile.status IS 'sortable';
COMMENT ON COLUMN profile.role IS 'sortable';

INSERT INTO profile(email, password, "name", surname, birth_date, about, gender, status, role)
VALUES ('admin@charm.ru', 'qwerty', 'Admin', NULL, NULL, NULL, NULL, 'INACTIVE', 'ADMIN'),
('ivanov@mail.ru', '123', 'Ivan', 'Ivanov', '2001-12-03', 'I am QA', 'MALE', 'ACTIVE', 'USER'),
('sidorova@mail.ru', '456', 'Elena', 'Sidorova', '1999-09-01', 'I am Java Dev', 'FEMALE', 'ACTIVE', 'USER');

CREATE TABLE "like"(
    from_profile BIGINT NOT NULL REFERENCES profile (id),
    to_profile BIGINT NOT NULL REFERENCES profile (id),
    "like" BOOLEAN NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (from_profile, to_profile)
);

INSERT INTO "like" (from_profile, to_profile, "like") VALUES 
(2, 3, TRUE), (3, 2, FALSE);