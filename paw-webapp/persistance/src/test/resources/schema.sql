create table if not exists userdata(
                                       userid     serial PRIMARY KEY,
                                       name       VARCHAR ( 50 ) unique NOT NULL,
                                        email      VARCHAR ( 50 ) unique NOT NULL,
                                        password   VARCHAR ( 50 ) NOT NULL,
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
create table if not exists Reputation (reviewid bigint not null, userid bigint not null, downvote boolean not null, upvote boolean not null, primary key (reviewid, userid));

create table if not exists reviewreport
(
    id           bigint  not null
    primary key,
    reportreason varchar(255),
    reviewid     integer not null
    constraint fkt7dkxu3o53gve13117enrc8gl
    references review,
    userid       integer not null
    constraint fkla8cfhl5mo5a1t1l4ccrtpx02
    references userdata,
    constraint uk8lgug7x3slgnl3jbj1fooy3f1
    unique (userid, reviewid)
    );

create table if not exists comment
(
    commentid bigint  not null
        primary key,
    date      bit(500),
    text      varchar(255),
    reviewid  integer not null
        constraint fk1w75ids3tepope04t0genajaq
            references review,
    userid    integer not null
        constraint fkr9129fcvmyhvm8jt1c6ai4unl
            references userdata
);

create table if not exists commentreport
(
    id           bigint  not null
        primary key,
    reportreason varchar(255),
    commentid    bigint  not null
        constraint fkbpirv4atuny7nw92pbi8f6avj
            references comment,
    userid       integer not null
        constraint fk8wyu211nhbw69mntk4emvd2pp
            references userdata,
    constraint uktc6fo3p3uk57suw1qcvgvb7u0
        unique (userid, commentid)
);













