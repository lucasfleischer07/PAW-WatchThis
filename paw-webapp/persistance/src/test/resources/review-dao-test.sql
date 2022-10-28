insert into userdata(userid,name,email,password,reputation,image,role) values(1,'brandyhuevo','mateoperezrivera@gmail.com','secret',0,null ,'user');
insert into userdata(userid,name,email,password,reputation,image,role) values(2,'brandyhuevo2','mateoperezrivera2@gmail.com','secret',0,null ,'user');
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (1,'toy story','Woody is stolen from his home by toy dealer Al McWhiggin.','1999','Animation','jhon lasseter','1:32',92,'movie',null);
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (2,'adventure time','A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers','2010-2018','Animation','jhon lasseter','1:32',92,'serie',null);
insert into review (reviewId,type,contentId,userId,name,description,rating,reputation) values (2,'movie',1,1,'great movie','loved it! great actors',5,0);
insert into review (reviewId,type,contentId,userId,name,description,rating,reputation) values (3,'serie',2,2,'bad tv show','dont recommend it!',2,0);
