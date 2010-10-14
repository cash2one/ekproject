select * from cs_xj_student a

SELECT * FROM QX_ADMIN_USER

select * from user_tables
where table_name like '%USER%'

select * from qx_user_school

select * from qx_admin_si

select * from qx_account_school  d where d.school_id = 1


--统计各学校的使用情况
 select school_id,town_id,c.id si,"10_log","10_log_teacher","10_dx_flow" from cs_school_use_state rpt left join xj_school on xj_school.id = rpt.school_id  left join qx_user_school b on b.school_id = rpt.school_id  left join qx_admin_si c on c.id = b.user_id  left join qx_account_school d on d.school_id = rpt.school_id  where year=2010 and town_id=3863 and d.user_id=1 and school_id=1 and c.id=1674
---



select * from qx_account_school
select * from qx_admin_user 

---
select town_name 县区,school_name 学校名称,
  b.teacher_nums 教师数,
  c.student_nums 学生数,d.jf_nums 计费数
  from xj_school a 
  left  join  (select count(*) teacher_nums,a.school_id  
      from xj_teacher a,xj_school b 
      where a.school_id=b.id group by a.school_id) b on a.id=b.school_id


  left join (select count(*) student_nums,a.school_id  from wz_xj_stu_class a,xj_school b 
  where a.school_id=b.id group by a.school_id) c  on a.id=c.school_id

  left join (select count(*) jf_nums,a.school_id from wz_xj_stu_class a,xj_school b 
     where a.id=b.id and exists(select 1 from wz_xj_family c,wz_tranpackage_customer d
     where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null) 
    group by a.school_id) d on d.school_id=a.id 


 
    select  school_id ,count(userid)  from xj_teacher group by school_id 
    
    select   school_id ,count(*)  from cs_xj_stu_class  group by school_id 
  
    select  


