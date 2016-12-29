select * from students;
insert into students(name,email,phone,dob) values('강보미3','kbm@test.co.kr','010-1111-1111','2016-12-12');
set foreign_key_checks=1;

update students set name="경아박",email="경아랑",phone="010-0000-1111",dob="2011-12-22" where stud_id=6;