create table public.clothes_brand
(
    id       varchar(36) not null
        primary key,
    homepage varchar(255),
    name     varchar(255)
);

create table public.outfit_suggestions
(
    id          varchar(36)  not null
        primary key,
    clothes_ids varchar(255),
    name        varchar(255),
    occasion    varchar(255),
    season      varchar(255),
    user_id     varchar(255) not null ,
    wheather    varchar(255)
);


create table public.clothing
(
    id               varchar(36)  not null
        primary key,
    availability           boolean,
    size            varchar(255),
    description      varchar(255),
    name             varchar(255) not null,
    user_id          varchar(255) not null,
    image_url          varchar(255),
    clothes_brand    varchar(36)
        constraint fk3vre6nbcq4q5jxiwop0xovuao
            references public.clothes_brand,
    clothes_category  varchar(255)
);

create table public.confirmation_dataset
(
    id               varchar(36)  not null
        primary key,
    confirmed_by_user          varchar(255),
    added_by_user          varchar(255),
    image_url          varchar(255),
    clothes_category  varchar(255),
    confirmed boolean


);


create table public.saved_outfits
(
    id          varchar(36)  not null
        primary key,
    clothes_ids varchar(255),
    name        varchar(255),
    occasion    varchar(255) not null,
    season      varchar(255) not null,
    user_id     varchar(255) not null
);


