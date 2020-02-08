CREATE TABLE IF NOT EXISTS public."user"
(
    id           BIGINT  NOT NULL,
    enabled      BOOLEAN NOT NULL,
    first_name   CHARACTER VARYING(255) COLLATE pg_catalog."default",
    last_name    CHARACTER VARYING(255) COLLATE pg_catalog."default",
    password     CHARACTER VARYING(255) COLLATE pg_catalog."default",
    phone_number CHARACTER VARYING(255) COLLATE pg_catalog."default",
    username     CHARACTER VARYING(255) COLLATE pg_catalog."default",
    token        CHARACTER VARYING(255) COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public."user"
    OWNER TO worktimer;

CREATE SEQUENCE IF NOT EXISTS user_sequence START 2 INCREMENT 1;
ALTER SEQUENCE user_sequence OWNER TO worktimer;

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
ALTER SEQUENCE role_sequence OWNER TO worktimer;


CREATE TABLE IF NOT EXISTS users_roles
(
    user_id BIGINT NOT NULL
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
ALTER SEQUENCE users_roles_sequence OWNER TO worktimer;

CREATE TABLE IF NOT EXISTS task
(
    id          BIGINT NOT NULL,
    name        CHARACTER VARYING(255) COLLATE pg_catalog."default",
    description CHARACTER VARYING(255) COLLATE pg_catalog."default",
    user_id BIGINT
        CONSTRAINT task_user___fk
            REFERENCES "user"
            ON DELETE SET NULL,
    CONSTRAINT task_pkey PRIMARY KEY (id)
);

ALTER TABLE task
    OWNER TO worktimer;

CREATE SEQUENCE IF NOT EXISTS task_sequence START 2 INCREMENT 1;
ALTER SEQUENCE task_sequence OWNER TO worktimer;


CREATE TABLE IF NOT EXISTS time_log
(
    id          BIGINT NOT NULL,
    start_time TIMESTAMP COLLATE pg_catalog."default",
    end_time TIMESTAMP COLLATE pg_catalog."default",
    task_id BIGINT
        CONSTRAINT time_log_task___fk
            REFERENCES task
            ON DELETE SET NULL,
    user_id BIGINT
        CONSTRAINT time_log_user___fk
            REFERENCES "user"
            ON DELETE SET NULL,
    CONSTRAINT time_log_pkey PRIMARY KEY (id)
);

ALTER TABLE time_log
    OWNER TO worktimer;

CREATE SEQUENCE IF NOT EXISTS time_log_sequence START 1 INCREMENT 1;
ALTER SEQUENCE time_log_sequence OWNER TO worktimer;


CREATE TABLE IF NOT EXISTS screenshot
(
    id     BIGINT NOT NULL,
    time_log_id BIGINT
        CONSTRAINT screenshot_time_log___fk
            REFERENCES time_log
            ON DELETE SET NULL,
    date TIMESTAMP COLLATE pg_catalog."default",
    base64 CHARACTER VARYING(255) COLLATE pg_catalog."default",
    CONSTRAINT screenshot_pkey PRIMARY KEY (id)
);

ALTER TABLE screenshot
    OWNER TO worktimer;

CREATE SEQUENCE IF NOT EXISTS screenshot_sequence START 1 INCREMENT 1;
ALTER SEQUENCE screenshot_sequence OWNER TO worktimer;
