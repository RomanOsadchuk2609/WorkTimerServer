CREATE TABLE IF NOT EXISTS public."user"
(
    id           BIGINT  NOT NULL,
    enabled      BOOLEAN NOT NULL,
    first_name   CHARACTER VARYING(255) COLLATE pg_catalog."default",
    last_name    CHARACTER VARYING(255) COLLATE pg_catalog."default",
    password     CHARACTER VARYING(255) COLLATE pg_catalog."default",
    phone_number CHARACTER VARYING(255) COLLATE pg_catalog."default",
    username     CHARACTER VARYING(255) COLLATE pg_catalog."default",
    TOken        CHARACTER VARYING(255) COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public."user"
    OWNER TO worktimer;

CREATE SEQUENCE IF NOT EXISTS user_sequence START 2 INCREMENT 1;
ALTER SEQUENCE user_sequence
    OWNER TO worktimer;

CREATE TABLE IF NOT EXISTS public.role
(
    id          BIGINT NOT NULL,
    description CHARACTER VARYING(255) COLLATE pg_catalog."default",
    name        CHARACTER VARYING(255) COLLATE pg_catalog."default",
    CONSTRAINT role_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.role
    OWNER TO worktimer;

CREATE SEQUENCE IF NOT EXISTS role_sequence START 3 INCREMENT 1;
ALTER SEQUENCE role_sequence
    OWNER TO worktimer;


CREATE TABLE IF NOT EXISTS users_roles
(
    user_id BIGINT not null
        CONSTRAINT fkgd3iendaoyh04b95ykqise6qh
            REFERENCES "user",
    role_id BIGINT NOT NULL
        CONSTRAINT fkt4v0rrweyk393bdgt107vdx0x
            REFERENCES role,
    CONSTRAINT users_roles_pkey
        PRIMARY KEY (user_id, role_id)
);

ALTER TABLE users_roles
    OWNER TO worktimer;

CREATE SEQUENCE IF NOT EXISTS users_roles_sequence START 3 INCREMENT 1;
ALTER SEQUENCE users_roles_sequence
    OWNER TO worktimer;
