CREATE DATABASE charm_repository;

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

CREATE TABLE "like"(
    from_profile BIGINT NOT NULL REFERENCES profile (id) ON DELETE CASCADE,
    to_profile BIGINT NOT NULL REFERENCES profile (id) ON DELETE CASCADE,
    "like" BOOLEAN NOT NULL,
    match BOOLEAN NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
    PRIMARY KEY (from_profile, to_profile)
);