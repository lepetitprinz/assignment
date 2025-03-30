create table category
(
    id uuid default random_uuid(),
    name varchar(255),
    primary key(id)
);

create table brand
(
    id uuid default random_uuid(),
    name varchar(255),
    primary key(id)
);

create table product
(
    id uuid default random_uuid(),
    name varchar(255),
    category_id uuid not null,
    brand_id uuid not null,
    price int not null,
    primary key(id)
);