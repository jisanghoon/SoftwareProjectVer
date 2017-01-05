select * from sale;
select * from buyer;
select * from software;
select * from company;

select distinct sw.title from software sw,sale s where sw.title=s.title and s.shopname = '아산시스템';