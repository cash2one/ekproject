-- 积分系统

-- 积分明细表
select * from am_centdetail


select * from am_tea_class_stat_fs


select * from fS_CENT_REPORT


select * from am_cent

select * from am_consume

select * from xj_class_teacher_jifen



select id from xj_school where school_name='天使无忧'


update xj_school
set school_name ='天使无忧'
where id = 76062


select * from xj_class 
where school_id=76062 and class_name ='小一(1)班'


select * from zs_yw_kf_chargerecord






/* Formatted on 2010/11/22 10:32 (Formatter Plus v4.8.8) */
SELECT   s.ID school_id, s.school_name, a.class_id, c.class_name, num
    FROM (SELECT   class_id,
                     SUM (CASE
                             WHEN TRANSACTION = 101
                                THEN 1
                             ELSE 0
                          END)
                   + SUM (CASE
                             WHEN TRANSACTION = 1
                                THEN 1
                             ELSE 0
                          END) num
              FROM zs_yw_kf_chargerecord  kf 
             WHERE charge_year = 2010
               AND charge_month = 11
               AND is_sf = 0
               AND chargestate = 'DELIVRD'
               AND EXISTS ( select 1 from zs_xj_family fa where fa.id = kf.family_id )
          GROUP BY ROLLUP (class_id)) a
         LEFT JOIN
         xj_class c ON c.ID = a.class_id
         LEFT JOIN xj_school s ON s.ID = c.school_id
   WHERE c.in_school = 1 AND c.class_type = 1 AND s.area_id = 1
ORDER BY s.school_name




select * from zs_xj_family 
where phonetype <>0

SELECT * FROM zs_yw_kf_chargerecord

update  zs_xj_family fa
set fa.phonetype = 1
where id = 4248046

select * from zs_xj_family 
where id = 4248033


update zs_yw_kf_chargerecord a 
set (a.school_id,a.class_id,parent_name,family_id,student_name,phone) = (
select sc.school_id,sc.class_id,fa.name,fa.id,stu.name,fa.phone from zs_xj_stu_class sc,xj_school sch,xj_class cla,zs_xj_family fa,zs_xj_student stu
where SC.SCHOOL_ID = sch.id and sc.class_id = cla.id
and cla.class_type = 1 and cla.in_school = 1
and fa.stu_sequence = sc.stu_sequence
and stu.stu_sequence = sc.stu_sequence
and sc.stu_sequence = '100900000508')
where a.id=13295742



select * from zs_yw_kf_chargerecord

select sc.school_id,sc.class_id,fa.name,fa.id,stu.name,stu.stu_sequence from zs_xj_stu_class sc,xj_school sch,xj_class cla,zs_xj_family fa,zs_xj_student stu
where SC.SCHOOL_ID = sch.id and sc.class_id = cla.id
and cla.class_type = 1 and cla.in_school = 1
and fa.stu_sequence = sc.stu_sequence
and stu.stu_sequence = sc.stu_sequence


select count(*) from zs_xj_stu_class sc,xj_class cla
 where cla.class_type = 1 and cla.in_school = 1 and sc.class_id = cla.id
 
 
 
 select * from am_cent
 
 select * from amass_xy where abb = 'zs'



select * from AMASS_XY


/* Formatted on 2010/11/22 14:11 (Formatter Plus v4.8.8) */
SELECT   s.ID school_id, s.school_name, a.class_id, c.class_name, num
    FROM (SELECT   class_id,
                     SUM (CASE
                             WHEN TRANSACTION = 101
                                THEN 1
                             ELSE 0
                          END)
                   + SUM (CASE
                             WHEN TRANSACTION = 1
                                THEN 1
                             ELSE 0
                          END) num
              FROM zs_yw_kf_chargerecord kf
             WHERE
               EXISTS (SELECT 1 FROM zs_xj_family fa WHERE fa.ID = kf.family_id AND fa.phonetype = 0)
               AND charge_year = 2010
               AND charge_month = 10
               AND is_sf = 0
               AND chargestate = 'DELIVRD'
          GROUP BY ROLLUP (class_id)) a
         LEFT JOIN
         xj_class c ON c.ID = a.class_id
         LEFT JOIN xj_school s ON s.ID = c.school_id
   WHERE c.in_school = 1 AND c.class_type = 1 AND s.area_id = 1
ORDER BY s.school_name


select * from zs_yw_kf_chargerecord

update zs_yw_kf_chargerecord
set charge_month=10


/* Formatted on 2010/11/22 14:18 (Formatter Plus v4.8.8) */
SELECT DISTINCT a.class_id, a.teacher_id, b.userid, b.username
           FROM xj_class_teacher_jifen a, xj_teacher b
          WHERE a.area_id = 1
            AND a.teacher_id IS NOT NULL
            AND a.class_id IS NOT NULL
            AND a.teacher_id <> 0
            AND a.teacher_id = b.ID
            
            
            
select * from am_cent

delete  from am_cent


select * from xj_class_teacher_jifen

select * from xj_teacher

/* Formatted on 2010/11/22 14:40 (Formatter Plus v4.8.8) */
SELECT DISTINCT a.class_id, a.teacher_id, b.userid, b.username
           FROM xj_class_teacher_jifen a, xj_teacher b
          WHERE a.area_id = 1
            AND a.teacher_id IS NOT NULL
            AND a.class_id IS NOT NULL
            AND a.teacher_id <> 0
            AND a.teacher_id = b.ID
            


            
select * from am_cent  


delete from  am_cent       
   


select * from zs_yw_kf_chargerecord            

select * from xj_class a,xj_school b
where a.school_id = b.id
and a.id = 884050


select * from xj_class_teacher_jifen


select * from xj_teacher
where userid like 'zsyuhailin'




