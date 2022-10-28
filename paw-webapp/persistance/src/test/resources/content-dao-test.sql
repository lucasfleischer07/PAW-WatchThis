
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (172,'Tonari no Totoro','description','1988','Animation Comedy Family','Hayao Miyazaki','1 hour 26 minutes',86,'movie',null);
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (501,'Toy Story 2','description','1999','Animation', 'John Lasseter','1:32',92,'movie',null);
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (10,'Il buono, il brutto, il cattivo','description','1966','Adventure Western','Sergio Leone','2 hours 41 minutes',161,'movie',null);
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (492,'Avrupa Yakasi','description','2004–2009','Comedy','Gülse Birsel','1 hour',60,'serie',null);
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (497,'Queer Eye','description','2018–','Reality-T V','David Collins','45 minutes',45,'serie',null);
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (2,'The Shawshank Redemption','description','1994','Drama','Frank Darabont','2 hours 22 minutes',142,'movie',null);
insert into userdata(userid,name,email,password,reputation,image,role) values (1,'brandyhuevo','mateoperezrivera@gmail.com','secret',0,null ,'user');
insert into userdata(userid,name,email,password,reputation,image,role) values (2,'brandyhuevo2','mateoperezrivera2@gmail.com','secret',0,null ,'user');
insert into userwatchlist(userid,contentid) values (1,172);
insert into userwatchlist(userid,contentid) values (2,172);
insert into userwatchlist(userid,contentid) values (1,10);
insert into userwatchlist(userid,contentid) values (2,2);
insert into review (reviewId,type,contentId,userId,name,description,rating,reputation) values (1,'movie',501,1,'great movie','loved it! great actors',5,0);
