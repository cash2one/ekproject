select distinct transaction from zs_tran_confirmstatus
group by transaction 



select * from gz_synlesson_booklog
order by operate_date desc
 
 
select * from yw_tran_operator


BASE_TRANSACTION

 
--pass cp 导入 
call pkg_admin_op_worktask.ps_main();
  

SELECT * FROM cp_business_log


SELECT * FROM GZ_CPMESSAGE


SELECT * FROM cp_mobiler


select * from gz_worktask_operation


select id,info1,info2,info3,info4,info5 from yw_tran_import
order by id desc 



select * from yw_tran_import
order by id desc 






--外呼导入开通过程


   tran         VARCHAR2,
--业务类型(1_短信箱,2_考勤,3_亲情,4_留言板,5_套餐,6_同步课堂,7_快乐学堂,8_营销方案,)
   abb          VARCHAR2,                                          -- 地区简写
   areaid       NUMBER,                                               --地区ID
   is_package   NUMBER,                                             --套餐地区
   operatorid   NUMBER,                                             --审核人ID
   book_type    NUMBER   
   
   
   
   IF tran='121' & abb ='gz'    
      insert into cp_business_log(phone,si_id,cp_id,business_id,open,open_date,book_type,operator,reason) values();
      VALUE 
           
      insert into cp_mobiler(mobile,reg_time,business_id,cp_id,order_time,order_type,isorder,is_charge,update_time,first_order_time)
  
   END
  
   
   select b. from yw_tran_operator b

   select * from yw_tran_import 

   select * from BASE_TRANSACTION
 
   select * from gz_tran_confirmstatus
   
   
   
   select * from yw_tran_import
   order by id desc