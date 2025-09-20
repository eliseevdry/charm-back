create DATABASE charm_repository;

create TABLE profile(
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
    version INT NOT NULL DEFAULT 0,
    created_date TIMESTAMP NOT NULL DEFAULT current_timestamp
);

comment on column profile.id is 'sortable';
comment on column profile.email is 'sortable';
comment on column profile."name" is 'sortable';
comment on column profile.surname is 'sortable';
comment on column profile.birth_date is 'sortable';
comment on column profile.gender is 'sortable';
comment on column profile.status is 'sortable';
comment on column profile.role is 'sortable';

create TABLE "like"(
    from_profile BIGINT NOT NULL REFERENCES profile (id) ON delete CASCADE,
    to_profile BIGINT NOT NULL REFERENCES profile (id) ON delete CASCADE,
    "like" BOOLEAN NOT NULL,
    match BOOLEAN NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
    PRIMARY KEY (from_profile, to_profile)
);