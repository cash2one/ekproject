declare
	TYPE T_MSG_REC IS RECORD(
		phone_fee  varchar2,
		action number,
		sp_service_id varchar2
	);
    
    TYPE T_MSG_ARR IS TABLE OF T_MSG_REC INDEX BY BINARY INTEGER;
    
    CURSOR c_msg is
        select id,phone_fee,action,p_service_id from misc_order_log where is_handled = 0;
        
    v_ret_code varchar2(100 char) := '';
begin
    execute immediate 'truncate table misc_order_log';
    
    insert into misc_order_log(phone_use,action,salemodalid) values
    ('13800138001',1,'CP-TEST'),
    ('13800138002',1,'CP-TEST');
    
    for r_tmp in c_msg loop
        misc_pkg.handle_misc_order('','',r_tmp.phone_fee,r_tmp.action,0,0,'',r_tmp.sp_service_id,v_ret_code);
        update misc_order_log set feature_str = v_ret_code where id = r_tmp.id;
    end loop;
    
    commit;
end;

--------------------------------------------------------------------------------

select * from tranpackage_define where name like '%ÓýÄÜ%'

select * from cp_customer

/*
truncate table cp_customer;
truncate table cp_tran_customer;
truncate table cp_tran_customer_log;
*/

/*
truncate table misc_order_relation;
truncate table misc_order_log;
truncate table hy_transaction_log;
truncate table ts_transaction_log;
*/

select * from misc_order_log order by create_date desc

select * from misc_order_relation

select * from ts_transaction_log order by open_date desc

select * from hy_transaction_log order by open_date desc

select * from family_phone where phone = '13800138000'

select * from ts_xj_family where phone = '13800138000'

select * from hy_xj_family where phone = '13800138000'

select * from ts_tranpackage_customer where phone = '13800138000'

select * from hy_tranpackage_customer where phone = '13800138000'

select * from cp_customer

select * from cp_tran_customer

select * from cp_tran_customer_log order by operate_date desc

select * from qtone_errors order by added_date desc nulls last

select * from cp_customer

alter table cp_customer
	add operator varchar2(30 char) default 'LOCAL';

--------------------------------------------------------------------------------