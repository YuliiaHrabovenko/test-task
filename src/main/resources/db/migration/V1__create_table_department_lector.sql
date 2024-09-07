create table department(
    id bigserial primary key,
    name varchar(255) not null,
    head varchar(255) not null
);

create table lector (
    id bigserial primary key,
    degree varchar(255) not null,
    salary int not null ,
    first_name varchar(255) not null,
    last_name varchar(255) not null
);

create table department_lector (
   department_id bigint not null,
   lector_id bigint not null,
   primary key (department_id, lector_id),
   foreign key (department_id) references department(id),
   foreign key (lector_id) references lector(id)
);
