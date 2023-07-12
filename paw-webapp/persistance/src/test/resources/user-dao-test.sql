
insert into userdata(userid,name,email,password,image,role) values(2,'brandyhuevo','mateoperezrivera@gmail.com','secret',null ,'user');
insert into userdata(userid,name,email,password,image,role) values(3,'brandyhuevo2','mateoperezrivera2@gmail.com','secret',null ,'user');
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (172,'Tonari no Totoro','description','1988','Animation Comedy Family','Hayao Miyazaki','1 hour 26 minutes',86,'movie',null);
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (501,'Toy Story 2','description','1999','Animation', 'John Lasseter','1:32',92,'movie',null);
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (10,'Il buono, il brutto, il cattivo','description','1966','Adventure Western','Sergio Leone','2 hours 41 minutes',161,'movie',null);
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (492,'Avrupa Yakasi','description','2004–2009','Comedy','Gülse Birsel','1 hour',60,'serie',null);
insert into userwatchlist(userid,contentid) values (2,172);
insert into userwatchlist(userid,contentid) values (2,10);
insert into userviewedlist(userid,contentid) values (2,501);
insert into userviewedlist(userid,contentid) values (2,10);
insert into userwatchlist(userid,contentid) values (3,492);
insert into userviewedlist(userid,contentid) values (3,492);

