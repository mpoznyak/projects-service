create table public.project
(
    id          bigint not null
        primary key,
    description varchar(255),
    name        varchar(255)
);

alter table public.project
    owner to postgres;

create table public.users
(
    id    bigint not null
        primary key,
    email varchar(255),
    name  varchar(255)
);

alter table public.users
    owner to postgres;

create table public.users_project
(
    project_id bigint not null
        constraint project_fk
            references project,
    user_id    bigint not null
        constraint users_fk
            references users
);

alter table public.users_project
    owner to postgres;

