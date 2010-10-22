select * from  zs_xj_family_view  fv   

select * from zs_xj_family_view where phone='13800138000'

select * from zs_transaction_log

   select *  from  zs_xj_family_view  fv   where ( fv.is_dxx = 1 
   or fv.is_kaoqin=1
    or fv.is_liuyanban=1 
    or fv.is_qin_qing_tel = 1 ) and phone='13422215791'
   
      select * from (
          select fv.id,fv.phone,fv.stu_sequence,' 家长短信箱' transaction ,'1' transaction_id   from zs_xj_family_view fv  where phone ='13422215791' and fv.is_dxx =1 
            union  all
          select fv.id,fv.phone,fv.stu_sequence,' 考勤' transaction ,'2' transaction_id    from zs_xj_family_view fv  where phone ='13422215791' and fv.is_kaoqin =1 
            union all 
          select fv.id,fv.phone,fv.stu_sequence,' 亲情电话' transaction ,'3' transaction_id    from zs_xj_family_view fv  where phone ='13422215791' and fv.is_liuyanban =1 
            union all
          select fv.id,fv.phone,fv.stu_sequence,' 电子留言板' transaction ,'4' transaction_id  from zs_xj_family_view fv  where phone ='13422215791' and fv.is_qin_qing_tel =1 
       ) baseTrans left join  zs_transaction_log
        
