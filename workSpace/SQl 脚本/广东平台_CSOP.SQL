select * from  zs_xj_family_view  fv   

select * from zs_xj_family_view where phone='13800138000'

  select * from preferential_package
 select * from yw_transaction

select * from fs_transaction_log where phone='13531800003'

   select * from adc_member_order_service

  select * from  zs_yw_kf_chargerecord
   
 select * from fs_transaction_log

select * from preferential_package

  select * from fs_preferential_packager pp where pp.pp_id in(360,460) and phone='13531800003'
  
  --- 
  select pp.name transaction,pp.REMARK,pp.IS_FREE is_charge, fp.del is_open,  tlog.open_date,tlog.book_type
   select fp.del 
   from fs_xj_family fa  
   left join ( 
        select  a.* ,pp.name,pp.remark    from fs_preferential_packager a  
     ---   where a.phone='13531800003'  
        left join preferential_package pp on  pp.id =a.pp_id  
     ---   where a.phone='13531800003'  
        ) fp  
   on fa.id = fp.f_id and fp.phone = fa.phone 
  left  join ( 
   select a.* from fs_transaction_log a, (  
         select  b.family_id,b.stu_sequence,b.package_id,b.phone,b.open,max(b.open_date) open_date  from  fs_transaction_log b 
         group by b.family_id,b.stu_sequence,b.package_id,b.open,b.phone  having (b.open = 1 or b.open = 2 ) and phone='13531800003') n 
          where n.family_id=a.family_id and a.stu_sequence=n.stu_sequence and a.package_id = n.package_id and a.open = n.open and a.open_date= n.open_date 
     )tlog     on tlog.family_id=fa.id and tlog.phone=fa.phone and tlog.open=1  and fp.pp_id=tlog.package_id
    -- where fa.phone='13531800003' 
  
   ---  where fa.phone='13531800003' 
      
        where fa.phone='13531800003' and fp.del = 1 


select * from preferential_package
select   *  from all_sms_query

select * from dx_all_sms
select * from dx_mobile_send
select * from dx_confirmsms


select * from gz_dx_sms
select * from gz_dx_recsms
select * from all_sms_query
select * from dx_groupsend

select * from config_cache d  where d.function_id in (
select id from qx_function where url like '/allsmsquery.do' )
