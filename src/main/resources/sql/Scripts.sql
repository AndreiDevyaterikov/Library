create table if not exists author
(
    id   integer primary key,
    name varchar not null
);

create table if not exists book
(
    id integer primary key,
    title varchar not null,
    count integer not null
);

create table if not exists book_author
(
    book_id integer not null,
    author_id integer not null,
    primary key (book_id, author_id),
    foreign key (book_id) references book(id),
    foreign key (author_id) references author(id)
    );

create table if not exists logbook
(
    id integer primary key,
    book_id integer not null,
    reader varchar not null,
    issue_date timestamp,
    return_date timestamp,
    status varchar,
    foreign key (book_id) references book(id)
);

insert into author(id, name)
values (1, 'test1'),
       (2, 'test2')