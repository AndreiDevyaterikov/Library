create sequence if not exists authors_id_seq start with 20;
create sequence if not exists books_id_seq start with 20;
create sequence if not exists logs_id_seq start with 20;
create sequence if not exists genres_id_seq start with 20;

create table if not exists authors
(
    id   integer primary key,
    name varchar not null
);

create table if not exists books
(
    id    integer primary key,
    title varchar not null,
    count integer not null
);

create table if not exists book_authors
(
    book_id   integer not null,
    author_id integer not null,
    primary key (book_id, author_id),
    foreign key (book_id) references books (id),
    foreign key (author_id) references authors (id)
);

create table if not exists logbook
(
    id          integer primary key,
    book_id     integer not null,
    reader      varchar not null,
    issue_date  timestamp,
    return_date timestamp,
    status      varchar,
    foreign key (book_id) references books (id)
);

create table if not exists genres
(
    id   integer primary key,
    name varchar not null
);

create table if not exists book_genres
(
    book_id  integer not null,
    genre_id integer not null,
    primary key (book_id, genre_id),
    foreign key (book_id) references books (id),
    foreign key (genre_id) references genres (id)
);

insert into authors (id, name)
values (1, 'Фёдор Достоевский'),
       (2, 'Александр Пушкин'),
       (3, 'Михаил Шолохов'),
       (4, 'Михаил Булгаков'),
       (5, 'Лев Толстой'),
       (6, 'Борис Васильев');

insert into genres (id, name)
values (1, 'Роман'),
       (2, 'Психологический реализм'),
       (3, 'Криминальный жанр'),
       (4, 'Философская сказка'),
       (5, 'Саспенс'),
       (6, 'Историческая проза'),
       (7, 'Исторический жанр'),
       (8, 'Любовный роман'),
       (9, 'Военная проза'),
       (10, 'Сатира'),
       (11, 'Фантастика'),
       (12, 'Рассказ'),
       (13, 'Научная фантастика'),
       (14, 'Повесть'),
       (15, 'Пьеса');

insert into books (id, title, count)
values (1, 'Преступление и наказание', 5),
       (2, 'Война и мир', 5),
       (3, 'Собачье сердце', 5),
       (4, 'Мастер и Маргарита', 5),
       (5, 'Судьба человека', 5),
       (6, 'А зори здесь тихие', 5),
       (7, 'Евгений Онегин', 5),
       (8, 'Моцарт и Сальери', 5);

insert into book_authors (book_id, author_id)
values (1, 1),
       (2, 5),
       (3, 4),
       (4, 4),
       (5, 3),
       (6, 6),
       (7, 2),
       (8, 2);

insert into book_genres (book_id, genre_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 1),
       (2, 4),
       (2, 6),
       (2, 7),
       (2, 8),
       (2, 9),
       (3, 10),
       (3, 13),
       (4, 1),
       (4, 8),
       (4, 10),
       (4, 11),
       (5, 12),
       (6, 14),
       (7, 1),
       (8, 15)
