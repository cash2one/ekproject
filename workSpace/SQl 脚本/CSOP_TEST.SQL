
select * from zs_adc_member_order 

select * from adc_member_order_service
where tran_id = 360 

select * from preferential_package 

select * from base_transaction

select * from zs_yw_kf_chargerecord
  
  

---- Ì×²Í²éÑ¯

/* Formatted on 2010/11/11 16:18 (Formatter Plus v4.8.8) */
SELECT fp.NAME TRANSACTION, fp.remark, fp.is_free is_charge, fp.del is_open,
       tlog.open_date, tlog.book_type
  FROM fs_xj_family fa
       LEFT JOIN
       (SELECT a.*, pp.NAME, pp.remark, pp.is_free
          FROM fs_preferential_packager a LEFT JOIN preferential_package pp
               ON pp.ID = a.pp_id
               ) fp ON fa.ID = fp.f_id AND fp.phone = fa.phone
       LEFT JOIN
       (SELECT a.*
          FROM fs_transaction_log a,
               (SELECT   b.family_id, b.stu_sequence, b.package_id, b.phone,
                         b.OPEN, MAX (b.open_date) open_date
                    FROM fs_transaction_log b
                GROUP BY b.family_id,
                         b.stu_sequence,
                         b.package_id,
                         b.OPEN,
                         b.phone
                  HAVING b.OPEN = 1 OR b.OPEN = 2) n
         WHERE n.family_id = a.family_id
           AND a.stu_sequence = n.stu_sequence
           AND a.package_id = n.package_id
           AND a.OPEN = n.OPEN
           AND a.open_date = n.open_date) tlog
       ON tlog.family_id = fa.ID
     AND tlog.phone = fa.phone
     AND tlog.OPEN = 1
     AND tlog.package_id = fp.pp_id
 WHERE fa.phone = '13826098926'
 



----------²éÑ¯µ±Ç°Ì×²Í×´Ì¬

 select  * from fs_xj_family fa,preferential_package a
 left join  (
       select * from ( 
         select * from  fs_preferential_packager  b
         where exists( select * from fs_xj_family c where c.phone ='13826098926' and b.f_id = c.id )  
        ) 
 ) e  on e.pp_id = a.id
 left join 
 (SELECT a.*
          FROM fs_transaction_log a,
               (SELECT   b.family_id, b.stu_sequence, b.package_id, b.phone,
                         b.OPEN, MAX (b.open_date) open_date
                    FROM fs_transaction_log b
                GROUP BY b.family_id,
                         b.stu_sequence,
                         b.package_id,
                         b.OPEN,
                         b.phone
                  HAVING b.OPEN = 1 OR b.OPEN = 2) n
         WHERE n.family_id = a.family_id
           AND a.stu_sequence = n.stu_sequence
           AND a.package_id = n.package_id
           AND a.OPEN = n.OPEN
           AND a.open_date = n.open_date
     ) tlog on tlog.family_id =  e.f_id and tlog.package_id = a.id
 left join (
      select a.family_id,b.TRAN_ID,b.FEE,b.TRAN_CODE,b.SERVICE_CODE,b.PORT,b.TYPE from fs_adc_member_order a,adc_member_order_service b
      where a.tran_code = b.tran_code 
      and b.type =2
      and exists( select * from fs_xj_family c where c.phone ='13826098926' and a.family_id = c.id ) 
  ) adc  on adc.TRAN_ID = a.id
 where  
 fa.phone ='13826098926' 
 and exists (select * from area where area.id = a.area_id and area.abb='fs')       
     
 
 
 
 
 
 
 select * from fs_adc_member_order
 
 select * from adc_member_order_service
     
 
 

 
 select * from  fs_preferential_packager  b
 where exists( select * from fs_xj_family c where c.phone ='13826098926' and b.f_id = c.id )  
 
 select * from fs_preferential_packager
 
 select * from fs_adc_member_order
 
 

 select * from preferential_package a
 where a.area_id = 10