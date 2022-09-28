create table if not exists content  (
                          id serial PRIMARY KEY,
                          name VARCHAR ( 100 ) NOT NULL,
                          image VARCHAR ( 500 ) ,
                          description VARCHAR ( 500 ) NOT NULL,
                          released VARCHAR ( 50 ) NOT NULL,
                          genre VARCHAR ( 100 ) NOT NULL,
                          creator VARCHAR ( 100 ) NOT NULL,
                          duration VARCHAR ( 50 ) NOT NULL,
                          durationNum integer NOT NULL,
                          rating integer NOT NULL,
                          type VARCHAR (10) NOT NULL CHECK (type = 'serie' OR type = 'movie'),
                            reviewsAmount integer default 0
);

create table if not exists  review (
                        reviewId serial primary key,
                        type VARCHAR (10) NOT NULL CHECK (type = 'serie' OR type = 'movie'),
                        contentid INT NOT NULL,
                        useriD INT NOT NULL,
                        name VARCHAR ( 50 ) NOT NULL,
                        description VARCHAR ( 500 ) NOT NULL,
                        rating INT NOT NULL DEFAULT 0,
                        FOREIGN KEY(contentid) references content(id),
                        UNIQUE(userID, contentid)
);

create table if not exists userdata
(
    userid     serial,
    name       varchar(50)                            not null,
    email      varchar(50)                            not null,
    password   varchar(50) not null,
    reputation numeric default 0.00                   not null,
    image      varbinary(2048),
    role varchar(10),
    primary key (userid),
    unique (name),
    unique (email)
    );



CREATE TABLE if not exists  userWatchlist(
                              id serial primary key,
                              userId int not null,
                              contentId int not null,
                              foreign key(userid) references userdata,
                              foreign key(contentid) references content);

create table if not exists quotes
(
    id      serial,
    english text,
    spanish text
);
