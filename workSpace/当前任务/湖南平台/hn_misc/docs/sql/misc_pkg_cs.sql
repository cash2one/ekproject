/*
truncate table cs_tranpackage_customer;
truncate table zz_tranpackage_customer;
truncate table qtone_errors;
truncate table misc_order_relation;
truncate table misc_order_log;
truncate table cs_transaction_log;
*/

select * from zz_tranpackage_customer
where phone = '13637411421'  --15274939124
order by last_update_timestamp desc 

select * from cs_tranpackage_customer
where phone = '13637411421'  --15274939124
order by last_update_timestamp desc 

select * from cs_transaction_log 
where phone = '13637411421'
order by id desc

select * from misc_order_log order by id desc --26

select * from misc_order_relation 
--where phone_use = '15973195209'
order by create_date desc --46

select * from family_phone where phone= '15018024987'  --15274939124

select * from qtone_errors order by added_date desc nulls last

select * from ts_tranpackage_customer

select * from tranpackage_define

insert into cs_tranpackage_customer(family_id,phone,boss_salemodalid,
xxt_salemodalid,start_date,adcdeal_date,adcdeal_result,adcdeal_suc)
values(:1,:2:,:3,:4,:5,sysdate,sysdate,1,'成功')

insert into cs_tranpackage_customer (family_id,phone,boss_salemodalid,  xxt_salemodalid,start_date,adcdeal_date,adcdeal_result,adcdeal_suc)  values(:1,:2 , :3,:4,sysdate,sysdate,1,'成功') 

insert into cs_tranpackage_customer (family_id,phone,boss_salemodalid,  xxt_salemodalid,start_date,adcdeal_date,adcdeal_result,adcdeal_suc)  values(:1,:2,:3,:4,sysdate,sysdate,'1','成功')

--------------------------------------------------------

select count(1) from tranpackage_define tdef
join qx_admin_si si on tdef.si_id = si.id
join qx_user_school qsch on si.id = qsch.user_id
join xj_school sch on qsch.school_id = sch.id
join cs_xj_stu_class stu_class on sch.id = stu_class.school_id
join cs_xj_family family on stu_class.stu_sequence = family.stu_sequence
where family.phone = :1 and tdef.salemodalid = :2

select * from cs_xj_stu_class

insert into cs_xj_family(id,phone) values(10,'13800138000')

select * from family_phone

select * from cs_tranpackage_customer