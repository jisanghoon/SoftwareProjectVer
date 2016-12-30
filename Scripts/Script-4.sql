show tables;
select * from post;
delete from post;
LOAD data LOCAL INFILE 'C:/Cities/16.txt' INTO table post   character set 'euckr' fields 
TERMINATED by '|' IGNORE 1 lines 
(@zipcode, @d, @sido, @d , @sigungu, @d, @d, @d, @d, @doro, @d, @d, @building1, @building2, @d, @d, @d, @d, @d ,@d, @d, @d, @d, @d, @d, @d) set zipcode=@zipcode, sido=@sido, sigungu=@sigungu, doro=@doro, building1=@building1, building2=@building2;

select * from post where sido ='대구광역시';

CREATE INDEX idx_post_sido On post(sido);
CREATE INDEX idx_post_doro ON post(doro);

select distinct sido from post order by sido;
SELECT * from post where sido='' and doro like '%용잠%';
