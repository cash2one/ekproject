CREATE OR REPLACE PROCEDURE PRO_CP_TRAN_OPEN(
 tran         VARCHAR2,                         --  cp 业务类型(1_短信箱,2_考勤,3_亲情)
 abb          VARCHAR2,                         --  地区简写
 areaid       NUMBER,                           --  地区ID
 operatorid   NUMBER,                           --  审核人ID
 book_type    NUMBER 
) 
IS
p_sql varchar2(500);
TYPE cursor_ref IS REF CURSOR;
c_record cursor_ref;
import_id number(6);
phone varchar2(20);
business_id number(6);
ispen number(2);
operator varchar2(20);
reason varchar2(100);
isis_charge number(3);
info1 varchar2(20);
info2 varchar2(20);
info3 varchar2(20);
info4 varchar2(20);
info5 varchar2(20);
tran  varchar2(20);
BEGIN
     
      p_sql:=' select a.id,a.phone,a.info1,a.info2,a.info3,a.info4,a.info5,cp.business_id,b.type operate_type,b.tran,b.reason '||
      ' from yw_tran_import a '||
      ' left join yw_tran_operator b on a.operator_id = b.id '||
      ' left join '||abb||'_xj_family fa on fa.stu_sequence = a.stu_sequence '||
      ' left join '||abb||'_tran_confirmstatus tc on to_char(tc.transaction) = b.tran and fa.id = tc.family_id '||
      ' left join cp_business cp on cp.cp_id = tc.transaction '||
      ' where a.operator_id  = b.id and a.type =2 and b.id ='||operatorid;
   
          
      open c_record for p_sql;--打开游标
          loop 
          
             fetch c_record into import_id,phone,info1,info2,info3,info4,info5,business_id,ispen,tran,reason;
                exit when c_record%notfound;
                 
                   
             IF tran in ('121') THEN
                   
                 -- 开通 
                 IF ispen IN (1) THEN   
                    
                     p_sql :=' update yw_tran_import set type=3 where type=2 and id='|| import_id;
                     EXECUTE IMMEDIATE p_sql;
                     COMMIT;
                      
     
                     p_sql :='insert into cp_business_log (phone,si_id,cp_id,business_id,open,open_date,book_type,reason) values('''||
                            phone||''',0,'||tran||','||business_id||',1,sysdate,'||book_type||','''||reson||''')';
                     EXECUTE IMMEDIATE p_sql;
                     COMMIT;
             
                                                                      
                     p_sql :='insert into cp_mobiler (mobile,reg_time,business_id,cp_id,order_time,order_type,'||
                     'isorder,is_charge,update_time,first_order_time)'||'values('''||
                     phone||''',sysdate,'|| business_id ||','||tran||',sysdate,'||book_type||',1,0,sysdate,sysdate)'; 
                       
             
                     EXECUTE IMMEDIATE p_sql;
                     COMMIT;
                 
         
                 END IF;
                 
      
      
                 -- 取消
                 IF ispen IN (0) THEN   
                    
                     insert into cp_business_log (phone,si_id,cp_id,business_id,open,open_date,book_type,operator,reason)
                     value()
                 
                     insert into cp_mobiler (mobile,reg_time,business_id,cp_id,order_time,order_type,
                     isorder,is_charge,update_time,first_order_time)
                     values()   
                 
                 END IF;
             END IF;
            
          end loop;--结束循环
      close cur_row;--关闭游标
      
      
      
     EXCEPTION
     WHEN NO_DATA_FOUND THEN
       NULL;
     WHEN OTHERS THEN
       RAISE;
END PRO_CP_TRAN_OPEN;



/


SELECT * FROM yw_tran_operator



select * from yw_tran_import




select * from cp_business_log
order by open_date desc




select * from cp_mobiler
order by order_time desc




--注意  此表中 transaction  3、4.5 就表示对应的 天天向上，健康导航
select * from gz_tran_confirmstatus
order by id desc

