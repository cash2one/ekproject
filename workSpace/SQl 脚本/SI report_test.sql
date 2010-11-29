select * from si_staticdata_stat

select * from SI_BANK_USER_STAT

delete from SI_BANK_USER_STAT


select * from school_bank  a
where  A.STUDENT_CNT >0 

select * from report_si_temp1


select * from xj_class


select * from area



  call PRO_XXT_SIREPORT_LOG();


  select area.name,qas.company,dat.* from (
         select a.*,B.STU_NUM from school_bank a,report_si_temp1 b
         where a.school_id = b.school_id and a.bank = b.bank
      )dat 
    LEFT JOIN area ON area.ID = dat.area_id
    LEFT JOIN qx_user_school qus ON qus.school_id = dat.school_id 
    LEFT JOIN qx_admin_si qas ON qus.user_id = qas.ID  



    select area.name,
         nvl(COUNT (DECODE (a.bank, 1, student_cnt, 0)),0 )y_school_num,
         nvl(SUM (DECODE (a.bank, 1, stu_num, 0)),0 ) y_stu_num,
         nvl(SUM (DECODE (a.bank, 1, student_cnt, 0)),0 ) y_stu_cnt,
         nvl(COUNT (DECODE (a.bank, 2, student_cnt,0)),0 )x_school_num,
         nvl(SUM (DECODE (a.bank, 2, stu_num, 0)) ,0 )x_stu_num,
         nvl(SUM (DECODE (a.bank, 2, student_cnt, 0)),0 ) x_stu_cnt,
         nvl(COUNT (DECODE (a.bank, 3, student_cnt, 5, student_cnt, 0) ),0 ) z_school_num,
         nvl(SUM (DECODE (a.bank, 3, stu_num, 0)),0 ) z_stu_num,
         nvl(SUM (DECODE (a.bank, 3, student_cnt, 5, student_cnt, 0)) ,0 )z_stu_cnt,
         nvl(COUNT (DECODE (a.bank, 4, student_cnt, 0)),0 ) g_school_cnt,
         nvl(SUM (DECODE (a.bank, 4, stu_num, 0)),0 ) g_stu_num,
         nvl(SUM (DECODE (a.bank, 4, student_cnt, 0)),0 ) g_stu_cnt
    from (
         select a.*,B.STU_NUM from school_bank a,report_si_temp1 b
         where a.school_id = b.school_id and a.bank = b.bank
     ) a
    LEFT JOIN area ON area.ID = a.area_id
   group by area.name
   order by area.name




select count(*) from school_bank

select count(*) from report_si_temp1


select * from qx_admin_si



/* Formatted on 2010/11/12 10:22 (Formatter Plus v4.8.8) */
SELECT   area.NAME, qas.company,
         COUNT (DECODE (a.bank, 1, student_cnt, NULL)) y_school_num,
         SUM (DECODE (t.bank, 1, stu_num, 0)) y_stu_num,
         SUM (DECODE (a.bank, 1, student_cnt, 0)) y_stu_cnt,
         COUNT (DECODE (a.bank, 2, student_cnt, NULL)) x_school_num,
         SUM (DECODE (t.bank, 2, stu_num, 0)) x_stu_num,
         SUM (DECODE (a.bank, 2, student_cnt, 0)) x_stu_cnt,
         COUNT (DECODE (a.bank, 3, student_cnt, 5, student_cnt, NULL)
               ) z_school_num,
         SUM (DECODE (t.bank, 3, stu_num, 0)) z_stu_num,
         SUM (DECODE (a.bank, 3, student_cnt, 5, student_cnt, 0)) z_stu_cnt,
         COUNT (DECODE (a.bank, 4, student_cnt, NULL)) g_school_cnt,
         SUM (DECODE (t.bank, 4, stu_num, 0)) g_stu_num,
         SUM (DECODE (a.bank, 4, student_cnt, 0)) g_stu_cnt
    FROM school_bank a LEFT JOIN report_si_temp1 t
         ON t.school_id = a.school_id AND a.bank = t.bank
         LEFT JOIN area ON area.ID = a.area_id
         LEFT JOIN qx_user_school qus ON qus.school_id = a.school_id and qus.school_id = t.school_id
         LEFT JOIN qx_admin_si qas ON qus.user_id = qas.ID
GROUP BY area.NAME, qas.company


select * from school_bank




select count(*) from xj_school a
where  A.AREA_ID = (select id from area where abb ='gz' )


select count(*) from xj_school a
where  exists (select id from area b where abb ='gz' and b.id = A.AREA_ID )




select count(*) from school_bank t 
where school_id not in ( 
     select id from xj_school a
     where  exists (select id from area b where abb ='gz' and b.id = A.AREA_ID ) 
)


select * from zs_dx_stustat

select * 