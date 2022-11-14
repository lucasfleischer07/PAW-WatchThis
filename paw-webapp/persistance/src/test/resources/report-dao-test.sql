insert into userdata(userid,name,email,password,image,role) values(1,'brandyhuevo','mateoperezrivera@gmail.com','secret',null ,'user');
insert into userdata(userid,name,email,password,image,role) values(2,'brandyhuevo2','mateoperezrivera2@gmail.com','secret',null ,'user');
insert into content(id,name,description,released,genre,creator,duration,durationnum,type,image) values (2,'adventure time','A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers','2010-2018','Animation','jhon lasseter','1:32',92,'serie',null);
insert into review (reviewId,type,contentId,userId,name,description,rating) values (3,'serie',2,1,'bad tv show','dont recommend it!',2);
insert into reviewreport(id,reportReason,reviewid,userid) values(3,'Other',3,1);
insert into comment(commentid,text,reviewid,userid) values(1,'comment',3,1);
insert into commentreport(id,reportReason,commentid,userid) values (3,'Inappropriate',1,1)