create table if not exists authors
(
    id   integer primary key,
    name varchar not null
);

create table if not exists books
(
    id integer primary key,
    title varchar not null,
    count integer not null
);

create table if not exists book_authors
(
    book_id integer not null,
    author_id integer not null,
    primary key (book_id, author_id),
    foreign key (book_id) references books(id),
    foreign key (author_id) references authors(id)
    );

create table if not exists logbook
(
    id integer primary key,
    book_id integer not null,
    reader varchar not null,
    issue_date timestamp,
    return_date timestamp,
    status varchar,
    foreign key (book_id) references books(id)
);