insert into userdata(userid,name,email,password,reputation,image,role) values(1,'brandyhuevo','mateoperezrivera@gmail.com','secret',0,null ,'user');
insert into userdata(userid,name,email,password,reputation,image,role) values(2,'brandyhuevo2','mateoperezrivera2@gmail.com','secret',0,null ,'user');
insert into content(id,name,description,released,genre,creator,duration,durationnum,rating,type,reviewsAmount,image) values (172,'Tonari no Totoro','description','1988','Animation Comedy Family','Hayao Miyazaki','1 hour 26 minutes',86,4,'movie',1,null);
insert into content(id,name,description,released,genre,creator,duration,durationnum,rating,type,reviewsAmount,image) values (501,'Toy Story 2','description','1999','Animation', 'John Lasseter','1:32',92,0,'movie',0,null);
insert into content(id,name,description,released,genre,creator,duration,durationnum,rating,type,reviewsAmount,image) values (10,'Il buono, il brutto, il cattivo','description','1966','Adventure Western','Sergio Leone','2 hours 41 minutes',161,0,'movie',0,null);
insert into content(id,name,description,released,genre,creator,duration,durationnum,rating,type,reviewsAmount,image) values (492,'Avrupa Yakasi','description','2004–2009','Comedy','Gülse Birsel','1 hour',60,0,'serie',0,null);
insert into userwatchlist(id,userid,contentid) values (1,1,172);
insert into userwatchlist(id,userid,contentid) values (2,1,10);
insert into userviewedlist(id,userid,contentid) values (1,1,501);
insert into userviewedlist(id,userid,contentid) values (2,1,10);
insert into userwatchlist(id,userid,contentid) values (3,2,492);
insert into userviewedlist(id,userid,contentid) values (3,2,492);

