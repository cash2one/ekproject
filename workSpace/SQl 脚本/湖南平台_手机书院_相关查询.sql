
select table_name  from user_tables where table_name like '%CP%'
/*  查询 业务*/

select *  from cp_transaction  where cp_id = 3 order by code
select   distinct  code  from cp_transaction  where cp_id = 3 order by code
select * from cp_provider


select * from cp_transaction_temp_zx


select * from cp_transaction where cp_id = 3  and id  = 341
select * from cp_customer  where phone = '13873160731'
select * from cp_tran_customer cus where cus.customer_id = (select id from cp_customer  where phone = '13873160731')
select * from cp_tran_customer_log where customer_id = 130675



/*建立一个用户*/
insert into cp_customer (password,phone,add_date) values ('123456','15917292802',sysdate);
insert into cp_tran_customer (customer_id,transaction_id,points,operate_date,del,boss_status) values(130675, 341,50,sysdate,0,'NOT_SYNCED' );
insert into cp_tran_customer_log(customer_id,transaction_id,operate_date,operate_type,operator) values(130675 ,341,sysdate,1,'hdadmin') 

/*对方*/

/*取消操作*/
insert into DX_SERVICESync_FX (servicecode,mobile,type,dt,state)
values('10110','15917292802',0,sysdate,0)


select * from cp_customer where phone ='13873160731'
