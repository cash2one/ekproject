CREATE OR REPLACE PROCEDURE PRO_CP_TRAN_OPEN IS
p_sql varchar2(500);
TYPE cursor_ref IS REF CURSOR;
c_record cursor_ref;
phone varchar2(20);
business_id number(6);
ispen number(2);
operator varchar2(20);
reason varchar2(100);
is_charge number(3);
BEGIN
      
     
      p_sql:=' select a.*,cp.business_id,b.type operate_type from yw_tran_import a '||
      ' left join yw_tran_operator b on a.operator_id = b.id '||
      ' left join gz_xj_family fa on fa.stu_sequence = a.stu_sequence '||
      ' left join gz_tran_confirmstatus tc on to_char(tc.transaction) = b.tran and fa.id = tc.family_id '||
      ' left join cp_business cp on cp.cp_id = tc.transaction '||
      ' where a.operator_id  = b.id and a.type =2 and b.id = 17532 ';
   
      
      
      open c_record for p_sql;--打开游标
          loop 
             fetch c_record into countNum,school_id;
                exit when c_record%notfound;
                 
                   
             insert into cp_business_log (phone,si_id,cp_id,business_id,open,open_date,book_type,operator,reason)
             value()
         
     
             insert into cp_mobiler (mobile,reg_time,business_id,cp_id,order_time,order_type,
             isorder,is_charge,update_time,first_order_time)
             values()
                
             
          end loop;--结束循环
      close cur_row;--关闭游标
      
      
      
     EXCEPTION
     WHEN NO_DATA_FOUND THEN
       NULL;
     WHEN OTHERS THEN
       -- Consider logging the error and then re-raise
       RAISE;
END PRO_CP_TRAN_OPEN;



/
