select * from cs_xj_student a

SELECT * FROM QX_ADMIN_USER

select * from user_tables
where table_name like '%USER%'

select * from qx_user_school

select * from qx_admin_si

select * from qx_account_school  d where d.school_id = 1


--ͳ�Ƹ�ѧУ��ʹ�����
   select * from 
   ( select school_id,town_id,c.id si,"10_log","10_log_teacher","10_dx_flow" 
     from cs_school_use_state rpt left join xj_school on xj_school.id = rpt.school_id  
     left join qx_user_school b on b.school_id = rpt.school_id  
     left join qx_admin_si c on c.id = b.user_id  
     left join qx_account_school d on d.school_id = rpt.school_id  
     where year=2010 and town_id=3863 and d.user_id=1 and school_id=1 and c.id=1674
    ) userpt
    left join  (
     select a.school_id,teaNum,stuNum,jfNum from 
    (   
      select school_id ,count(userid) teaNum  from xj_teacher group by school_id
     )a
    left join (
        select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id 
    ) b on a.school_id = b.school_id
    left join (
        select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b 
        where a.id=b.id and exists(select 1 from cs_xj_family c,wz_tranpackage_customer d
        where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null) 
        group by a.school_id 
    )c on c.school_id = a.school_id
    ) base  on base.school_id = userpt.school_id

  


---



select * from qx_account_school
select * from qx_admin_user 

---
select school_name ѧУ����,
  b.teacher_nums ��ʦ��,
  c.student_nums ѧ����,d.jf_nums �Ʒ���
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


    select a.school_id,teaNum,stuNum,jfNum from 
    (   
      select school_id ,count(userid) teaNum  from xj_teacher group by school_id
     )a
    left join (
        select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id 
        union all
        select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id
    ) b on a.school_id = b.school_id
    left join (
        select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b 
        where a.id=b.id and exists(select 1 from cs_xj_family c,wz_tranpackage_customer d
        where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null) 
        group by a.school_id 
    )c on c.school_id = a.school_id



       select * from(select * from cs_xj_stu_class union all select * from cs_xj_stu_class )
