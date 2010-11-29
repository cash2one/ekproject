-- 缺失
13875823666
13755122333
13607442528
13875833332


-- CP业务信息
select * from cp_transaction a  
--where cp_id = 3 
order by code desc



---查询 日志
select  a.*  from cp_tran_customer_log a
left join cp_transaction b on a.transaction_id = B.ID
where 
operate_date >= to_date('2010-11-25 17:00:00','yyyy-mm-dd hh24:mi:ss')
--and B.CP_ID = 3
and operate_date <= to_date('2010-11-26 00:00:00','yyyy-mm-dd hh24:mi:ss')
--and operator='MISC'
--and a.operate_type = 1
--and customer_id in ( select id from cp_customer where phone ='15973168583')
--and a.transaction_id in (select id from cp_transaction a  where cp_id = 3 )
ORDER BY CUSTOMER_ID


select  count(*)  from cp_tran_customer_log a
left join cp_transaction b on a.transaction_id = B.ID
where 
operate_date >= to_date('2010-11-25 17:00:00','yyyy-mm-dd hh24:mi:ss')
--and B.CP_ID = 3
and operate_date <= to_date('2010-11-26 00:00:00','yyyy-mm-dd hh24:mi:ss')
--and operator='MISC'
--and a.operate_type = 1
--and customer_id in ( select id from cp_customer where phone ='15973168583')
--and a.transaction_id in (select id from cp_transaction a  where cp_id = 3 )
ORDER BY CUSTOMER_ID


--查询订购关系
select count(*) from cp_tran_customer a left join cp_transaction b on a.transaction_id =b.id
where  a.transaction_id  in (select id from cp_transaction a  where cp_id = 2 )  
and a.operate_date >= to_date('2010-11-25 14:00:00','yyyy-mm-dd hh24:mi:ss')
and a.operate_date <= to_date('2010-11-26 00:00:00','yyyy-mm-dd hh24:mi:ss')


select * from cp_tran_customer a left join cp_transaction b on a.transaction_id =b.id
where  a.transaction_id  in (select id from cp_transaction a  where cp_id = 2 )  
and a.operate_date >= to_date('2010-11-25 14:00:00','yyyy-mm-dd hh24:mi:ss')
and a.operate_date <= to_date('2010-11-26 00:00:00','yyyy-mm-dd hh24:mi:ss')


--查询对应的用户
select * from cp_customer where phone ='13822110000'


select * from cp_customer
where operator ='MISC' 
and add_date >= to_date('2010-11-25 14:00:00','yyyy-mm-dd hh24:mi:ss')
and add_date <= to_date('2010-11-26 15:00:00','yyyy-mm-dd hh24:mi:ss')


select count(*) from cp_customer
where operator ='MISC' 
and add_date >= to_date('2010-11-25 14:00:00','yyyy-mm-dd hh24:mi:ss')
and add_date <= to_date('2010-11-26 15:00:00','yyyy-mm-dd hh24:mi:ss')


--查询业务对应状态
select * from cp_tran_customer a
where customer_id in (select id from cp_customer where phone ='13822110000')



--select count(*) from  (
select distinct a.customer_id,a.transaction_id,b.name,b.code ,a.operate_type,a.operate_date from cp_tran_customer_log a
left join cp_transaction b on a.transaction_id = B.ID
where B.CP_ID = 2 and operate_date >= to_date('2010-11-25 11:00:00','yyyy-mm-dd hh24:mi:ss')
and operate_date <= to_date('2010-11-26 00:00:00','yyyy-mm-dd hh24:mi:ss')
and operator='MISC'
ORDER BY CUSTOMER_ID
--)

select * from cp_tran_customer_log
--  过滤重复日志 数目

select count(*) from  (
select *  from cp_tran_customer_log a
left join cp_transaction b on a.transaction_id = B.ID
where B.CP_ID = 3 and operate_date >= to_date('2010-11-17 18:00:00','yyyy-mm-dd hh24:mi:ss')
and operate_date <= to_date('2010-11-18 00:00:00','yyyy-mm-dd hh24:mi:ss')
--and operator='MISC'
ORDER BY CUSTOMER_ID
)




--查询 订购的新建的 业务日志
select * from  (
    select *  from cp_tran_customer_log a
    left join cp_transaction b on a.transaction_id = B.ID
    left join cp_customer c on a.customer_id = c.id   
    where B.CP_ID = 3 and operate_date >= to_date('2010-11-17 17:00:00','yyyy-mm-dd hh24:mi:ss')
    and operate_date <= to_date('2010-11-19 00:00:00','yyyy-mm-dd hh24:mi:ss')
   -- and operator='MISC'
    and c.phone = '13975106009'
    ORDER BY CUSTOMER_ID
)


---查看新导入的用户
select * from cp_customer
where  
--operator ='MISC' 
add_date >= to_date('2010-11-17 16:00:00','yyyy-mm-dd hh24:mi:ss')
and add_date <= to_date('2010-11-18 00:00:00','yyyy-mm-dd hh24:mi:ss')








select * from cp_tran_customer_log a
where exists (select * from cp_transaction b where b.cp_id=3 and b.id = a.transaction_id )
order by A.CUSTOMER_ID  



--- 业务中间表
select * from cp_transaction_temp_zx


select count(*) from cp_transaction_temp_zx


select * from  misc_order_relation


select * from tranpackage_define