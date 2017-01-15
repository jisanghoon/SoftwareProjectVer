use swmng;
select * from sale;

select shopName,sum(m) as `sum` from (select shopname,orderCount*sellPrice m from sale) temp group by shopName;
select shopname,orderCount*sellPrice m from sale ;

select shopname, orderCount,sellPrice from sale group by shopName with rollup;  