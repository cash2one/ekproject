select * from  zs_xj_family_view  fv   

select * from zs_xj_family_view where phone='13800138000'

   
      select base.id family,base.phone,base.stu_sequence,base.transaction,base.transaction_id,base.is_open,tlog.operator,tlog.reason,tlog.package_id,book_type,open_date from (
             select fv.id,fv.phone,fv.stu_sequence,' 家长短信箱' transaction ,'1' transaction_id,is_dxx is_open   from zs_xj_family_view fv  where phone ='13770536428' 
              union  all
             select fv.id,fv.phone,fv.stu_sequence,' 考勤' transaction ,'2' transaction_id ,is_kaoqin is_open   from zs_xj_family_view fv  where phone ='13770536428' 
             union all 
             select fv.id,fv.phone,fv.stu_sequence,' 亲情电话' transaction ,'3' transaction_id , is_qin_qing_tel is_open  from zs_xj_family_view fv  where phone ='13770536428'  
             union all
             select fv.id,fv.phone,fv.stu_sequence,' 电子留言板' transaction ,'4' transaction_id , fv.is_liuyanban as is_open  from zs_xj_family_view fv  where phone ='13770536428'  
       ) base left join (
                select a.* from zs_transaction_log a,
                (      
                        select    b.family_id,b.stu_sequence,b.transaction,b.phone,b.open,max(b.open_date) open_date  from  zs_transaction_log b
                         group by b.family_id,b.stu_sequence,b.transaction,b.open,b.phone     
                 ) n  
                    where  a.stu_sequence=n.stu_sequence and a.transaction = n.transaction and a.open = n.open and a.open_date= n.open_date 
       )  tlog 
        on tlog.family_id = base.id and tlog.stu_sequence = base.stu_sequence  and  tlog.open = base.is_open and tlog.transaction = base.transaction_id
        
       select * from  zs_transaction_log
