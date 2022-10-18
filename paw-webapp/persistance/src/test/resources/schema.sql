create table if not exists userdata(
                                       userid     serial PRIMARY KEY,
                                       name       VARCHAR ( 50 ) unique NOT NULL,
                                        email      VARCHAR ( 50 ) unique NOT NULL,
                                        password   VARCHAR ( 50 ) NOT NULL,
                                        reputation numeric default 0.00                   not null,
                                        image BIT ( 500 ) ,
                                        role VARCHAR ( 50 ) NOT NULL
                                        );

create table if not exists content  (
                          id serial PRIMARY KEY,
                          name VARCHAR ( 100 ) NOT NULL,
                          image BIT ( 500 ) ,
                          description VARCHAR ( 500 ) NOT NULL,
                          released VARCHAR ( 50 ) NOT NULL,
                          genre VARCHAR ( 100 ) NOT NULL,
                          creator VARCHAR ( 100 ) NOT NULL,
                          duration VARCHAR ( 50 ) NOT NULL,
                          durationNum integer NOT NULL,
                          type VARCHAR (10) NOT NULL CHECK (type = 'serie' OR type = 'movie'),
);

create table if not exists  review (
                        reviewId serial PRIMARY KEY,
                        type VARCHAR (10) NOT NULL CHECK (type = 'serie' OR type = 'movie'),
                        contentid INT NOT NULL,
                        useriD INT NOT NULL,
                        name VARCHAR ( 50 ) NOT NULL,
                        description VARCHAR ( 500 ) NOT NULL,
                        rating INT NOT NULL DEFAULT 0,
                        FOREIGN KEY(contentid) references content(id),
                        UNIQUE(userID, contentid)
);



CREATE TABLE if not exists  userwatchlist(
                              id serial primary key,
                              userId int not null,
                              contentId int not null,
                              foreign key(userid) references userdata,
                              foreign key(contentid) references content on delete cascade );

create table if not exists quotes
(
    id      serial,
    english text,
    spanish text
);

create table if not exists userviewedlist
(
    id        serial
        primary key,
    userid    integer not null
        references userdata,
    contentid integer not null
        references content on delete cascade
);






