/* Formatted on 2010/11/10 14:27 (Formatter Plus v4.8.8) */
 
 
  select * from  zs_adc_member_order ao 
           left join adc_member_order_service adc 
           on ao.tran_id = ADC.TRAN_ID
           and ao.tran_code = ADC.TRAN_CODE
 where phone = '13560170011'
 
 
 
 select * from area
 where is_package >=1
 
 

  select * from zs_yw_kf_chargerecord
 
  select * from adc_member_order_service a
 
  select * from zs_adc_member_order a
  
  
  
/* Formatted on 2010/11/10 17:07 (Formatter Plus v4.8.8) */
SELECT base.ID family, base.phone, base.stu_sequence, base.TRANSACTION,
       base.transaction_id, base.is_open, tlog.OPERATOR, tlog.reason,
       tlog.package_id, book_type, base.open_date, is_charge,
       NVL (ywt.fee, 0) fee, ywt.TYPE ywt_charge_type, ywt.tran_code,
       kf.update_date kf_date
  FROM (SELECT fv.ID, fv.phone phone, fv.stu_sequence, 'DXX' TRANSACTION,
               '1' transaction_id, is_dxx is_open,
               xj_school.is_dxx_charge is_charge, open_dxx_date open_date
          FROM zs_xj_family fv LEFT JOIN xj_school ON fv.school_id =
                                                                  xj_school.ID
         WHERE fv.phone = '15917292802'
        UNION
        SELECT fv.ID, fv.phone phone, fv.stu_sequence, 'KAOQIN' TRANSACTION,
               '2' transaction_id, is_kaoqin is_open,
               xj_school.is_kq_charge is_charge, open_kaoqin_date open_date
          FROM zs_xj_family fv LEFT JOIN xj_school ON fv.school_id =
                                                                  xj_school.ID
         WHERE fv.phone = '15917292802'
        UNION
        SELECT fv.ID, fv.phone phone, fv.stu_sequence, 'QINQING' TRANSACTION,
               '3' transaction_id, is_qin_qing_tel is_open,
               xj_school.is_qq_charge is_charge, open_qinqing_tel open_date
          FROM zs_xj_family fv LEFT JOIN xj_school ON fv.school_id =
                                                                  xj_school.ID
         WHERE fv.phone = '15917292802'
        UNION
        SELECT fv.ID, fv.phone phone, fv.stu_sequence,
               'LIUYANBAN' TRANSACTION, '4' transaction_id,
               is_liuyanban is_open, xj_school.is_lyb_charge is_charge,
               open_liuyanban open_date
          FROM zs_xj_family fv LEFT JOIN xj_school ON fv.school_id =
                                                                  xj_school.ID
         WHERE fv.phone = '15917292802') base
       LEFT JOIN
       (SELECT a.*
          FROM zs_transaction_log a,
               (SELECT   b.family_id, b.stu_sequence, b.TRANSACTION, b.phone,
                         b.OPEN, MAX (b.open_date) open_date
                    FROM zs_transaction_log b
                GROUP BY b.family_id,
                         b.stu_sequence,
                         b.TRANSACTION,
                         b.OPEN,
                         b.phone
                  HAVING b.OPEN = 1 OR b.OPEN = 0) n
         WHERE n.family_id = a.family_id
           AND a.stu_sequence = n.stu_sequence
           AND a.TRANSACTION = n.TRANSACTION
           AND a.OPEN = n.OPEN
           AND a.open_date = n.open_date) tlog
       ON tlog.family_id = base.ID
     AND tlog.stu_sequence = base.stu_sequence
     AND tlog.OPEN = base.is_open
     AND tlog.TRANSACTION = base.transaction_id
       LEFT JOIN zs_yw_kf_chargerecord kf
       ON kf.family_id = base.ID
     AND kf.phone = base.phone
     AND base.transaction_id = kf.TRANSACTION
       LEFT JOIN
       (SELECT adc.tran_id, adc.fee, adc.tran_code, adc.service_code,
               adc.port, adc.TYPE
          FROM zs_adc_member_order ao LEFT JOIN adc_member_order_service adc
               ON ao.tran_id = adc.tran_id AND ao.tran_code = adc.tran_code
               ) ywt ON ywt.tran_id = base.transaction_id
 WHERE 1 = 1
 
 
 select * from zs_adc_member_order
 
 
 select * from adc_member_order_service
 
 
 select * from zs_xj_family a