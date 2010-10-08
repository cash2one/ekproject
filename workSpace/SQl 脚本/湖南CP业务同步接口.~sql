select * from cp_tran_customer
select * from cp_transaction

select * from user_tables
where table_name like '%CP%'


select * from QX_ADMIN_USER
select * from CP_ADMIN


CREATE TABLE cp_transaction_temp_zx( 
      id  NUMBER(11),
      customer_id   NUMBER(6) ,
      transaction_id NUMBER(4),
      del       NUMBER(1),
      deal_state NUMBER(1),
      operate_date date
)


create table TEMP_DX_SERVICESync_ZX(
   id NUMBER(11),
   ServiceCode VARCHAR2(20),
   Phone VARCHAR2(20),
   Type NUMBER(1),
   Dt  DATE
)

create  sequence test_cpt_seq_zx   increment   by   1   start   with   1  maxvalue   999999999;
 create or replace trigger test_cpt_seq_zx  before insert  on TEMP_DX_SERVICESync_ZX 
for each row     
begin
       select test_cpt_seq_zx.nextval into :new.id  from dual;
end;


create table TEMP_DX_SERVICESync_FX(
   id NUMBER(11),
   ServiceCode VARCHAR2(20),
   Phone VARCHAR2(20),
   Type NUMBER(1),
   Dt  DATE,
   STATE NUMBER(1)
)

create  sequence test_cpt_seq_fx   increment   by   1   start   with   1  maxvalue   999999999;
 create or replace trigger test_cpt_seq_fx  before insert  on TEMP_DX_SERVICESync_FX 
for each row     
begin
       select test_cpt_seq_fx.nextval into :new.id  from dual;
end;


alter table  cp_tran_customer_test add  syn_type varchar2(8) default 'ZX' ;




/* 云博中间表  反向业务 */
select * from temp_dx_servicesync_fx
delete from temp_dx_servicesync_fx





/* 订购关系 表   临时测试表 */
select * from cp_tran_customer_test
delete from cp_tran_customer_test


/*新增 订购关系 记录*/
insert into cp_tran_customer_test (customer_id,transaction_id,points,del,operate_date) values(118123,83,60,1,sysdate);

/*模仿更改 订购业务操作*/
update cp_tran_customer_test
set cp_tran_customer_test.del  = 1
where cp_tran_customer_test.id = 308485

/*把实际的业务关系复制到  测试表中 */
insert into cp_tran_customer_test 
select * from cp_tran_customer

select * from cp_tran_customer where transaction_id = 421
select * from cp_transaction where code = 'SJSYSF'

/*反向测试*/
select  * from temp_dx_servicesync_fx
insert into temp_dx_servicesync_fx  
values(0,'SJSYSF','13800138000',1,sysdate,0);

select * from cp_tran_customer  where 
select * from cp_transaction_temp_fx

update temp_dx_servicesync_fx  
set temp_dx_servicesync_fx.type =0,state = 0,mobile ='15917292802'
where servicecode ='SJSYSF' and mobile ='15917292802'

select * from cp_tran_customer_log logs
where logs.operator ='hd'
order by operate_date desc

select * from cp_customer

select id from cp_customer where phone = ('15917292802')

/* 订购关系 表   临时测试表 */
select * from cp_tran_customer_test
delete from cp_tran_customer_test


/*正向关系 中间表*/
select * from cp_transaction_temp_zx
delete from cp_transaction_temp_zx

/* 云博中间表  正向业务 */
select * from temp_dx_servicesync_zx
delete from temp_dx_servicesync_zx

update cp_tran_customer_test
	    	SET del =1
	    	where id in (
	    	    select tran.id from cp_tran_customer tran join cp_transaction cp on cp.id = tran.transaction_id
	    	    join cp_customer cus on cus.id = tran.customer_id
	    	    where  cus.phone = '13800138000'  and  cp.code = 'SJSYSF' )


 select fx.ServiceCode,fx.Phone,fx.type,to_char(fx.dt,'yyyy-mm-dd hh24:mi') 
 from TEMP_DX_SERVICESync_ZX fx  where fx. = 0  and fx.id <= 13 and fx.type = 0  and fx.servicecode in ('SJSYSF','101110' ) 


 update cp_tran_customer_test set del= 1,operate_date = '2010-09-19 16:32' 
 where id = ( select tran.id from cp_tran_customer tran join cp_transaction cp on cp.id = tran.transaction_id   join cp_customer cus on cus.id = tran.customer_id  where  cus.phone = '13800138000' and  cp.code = 'SJSYSF'  )

select * from cp_transaction 
where name like '%手机%'


select  phone from cp_customer
where phone in ('13800138000')

sysdate

select * from  cp_tran_customer

 select * from cp_tran_customer cptu join cp_transaction tran on tran.id = cptu.transaction_id
	    	    join cp_customer cus on cus.id = cptu.customer_id
	    	    where  cus.phone = '13800138000'  and  tran.code = 'SJSYSF'
	    	    and tran.cp_id = 3
            
select * from     cp_transaction   ct
where name like '手机%'


 select cptu.id from cp_tran_customer cptu join cp_transaction tran on tran.id = cptu.transaction_id
	    	    join cp_customer cus on cus.id = cptu.customer_id
	    	    where  cus.phone = '13800138000'  and  tran.code = 'SJSYSF'
	    	     and tran.cp_id = 3 

 update cp_tran_customer set del= 1,operate_date = sysdate  where del=0
  and id = ( select cptc.id from cp_tran_customer cptc join cp_transaction tran 
  on tran.id = cptc.transaction_id   join cp_customer cus on cus.id = cptc.customer_id 
  where  cus.phone = '13800138000' and  tran.code = 'SJSYSF'  ) and tran.cp_id = 3

select * from cp_transaction

select  tran.id from cp_transaction tran  where tran.cp_id =3 and tran.code='SJSYSF'


select * from cp_customer where phone ='15917292802'
select * from cp_tran_customer where customer_id = 118183
select * from cp_transaction where id = 341



/* 反向模拟*/
 select * from temp_dx_servicesync_fx
 delete from temp_dx_servicesync_fx

  
insert into temp_dx_servicesync_fx  
values(0,'101110','13826098956',0,sysdate,0);


select * from temp_dx_servicesync_zx
delete from temp_dx_servicesync_zx

select * from cp_transaction_temp_zx
delete from cp_transaction_temp_zx
select * from cp_tran_customer_log   order by operate_date desc

select * from cp_customer where phone  = '13826098936'
