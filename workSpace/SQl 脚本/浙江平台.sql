select * from cs_xj_student a

SELECT * FROM QX_ADMIN_USER

select * from user_tables
where table_name like '%USER%'

select * from qx_user_school

select * from qx_admin_si

select * from qx_account_school  d where d.school_id = 1


--统计各学校的使用情况
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
select school_name 学校名称,
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



		    ) b on a.school_id = b.school_id
		    left join (
		        select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b 
		        where a.id=b.id and exists(select 1 from cs_xj_family c,cs_tranpackage_customer d
		        where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null) 
		        group by a.school_id 
          
		    )c on c.school_id = a.school_id
        
        where b.school_id  =1



select area.name area_name,mrpt.* from (select area_id,count(school_id) schoolNum,sum(teaNum) teaNum,sum(stuNum)stuNum,sum(jfNum)jfNum,sum(alogs)alogs,sum(tlogs)tlogs,sum(dx_flow)dx_flow	 from( select crpt.*, brpt.teaNum,brpt.stuNum,brpt.jfNum from (  select xj_school.area_id,xj_school.school_name school,town_id,c.id sid,rpt.* from(  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 ) rpt  left join xj_school on xj_school.id = rpt.school_id  left join qx_user_school b on b.school_id = rpt.school_id   left join qx_admin_si c on c.id = b.user_id   left join qx_account_school d on d.school_id = rpt.school_id where d.user_id= 1 and town_id=3863 and school_id=1 and c.id=1674 )crpt left join (select a.school_id,NVL(teaNum,0) teaNum ,NVL(stuNum,0) stuNum,NVL(jfNum,0) jfNum from (select school_id ,count(userid) teaNum  from xj_teacher group by school_id )a left join (select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id)b on a.school_id = b.school_id left join (  select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from cs_xj_family c,cs_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from cs_xj_family c,cs_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id )c on a.school_id = c.school_id )brpt on crpt.school_id = brpt.school_id ) group by area_id )mrpt left join area on area.id= mrpt.area_id 
 select town.name town_name,mrpt.* from ( select town_id,count(school_id) schoolNum,sum(teaNum) teaNum,sum(stuNum)stuNum,sum(jfNum)jfNum,sum(alogs)alogs,sum(tlogs)tlogs,sum(dx_flow)dx_flow from( select crpt.*, brpt.teaNum,brpt.stuNum,brpt.jfNum from (  select xj_school.area_id,xj_school.school_name school,town_id,c.id sid,rpt.* from(  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 ) rpt  left join xj_school on xj_school.id = rpt.school_id  left join qx_user_school b on b.school_id = rpt.school_id   left join qx_admin_si c on c.id = b.user_id   left join qx_account_school d on d.school_id = rpt.school_id where d.user_id= 1 and town_id=3863 and school_id=1 and c.id=1674 )crpt left join (select a.school_id,NVL(teaNum,0) teaNum ,NVL(stuNum,0) stuNum,NVL(jfNum,0) jfNum from (select school_id ,count(userid) teaNum  from xj_teacher group by school_id )a left join (select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id)b on a.school_id = b.school_id left join (  select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from cs_xj_family c,cs_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from cs_xj_family c,cs_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id )c on a.school_id = c.school_id )brpt on crpt.school_id = brpt.school_id ) group by town_id )mrpt left join town on mrpt.town_id=town.id  
 select town_id, town.name town_name,school 学校,QX_ADMIN_SI.company SI,teaNum ,stuNum ,jfNum ,alogs ,tlogs ,dx_flow   from( select crpt.*, brpt.teaNum,brpt.stuNum,brpt.jfNum from (  select xj_school.area_id,xj_school.school_name school,town_id,c.id sid,rpt.* from(  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 ) rpt  left join xj_school on xj_school.id = rpt.school_id  left join qx_user_school b on b.school_id = rpt.school_id   left join qx_admin_si c on c.id = b.user_id   left join qx_account_school d on d.school_id = rpt.school_id where d.user_id= 1 and town_id=3863 and school_id=1 and c.id=1674 )crpt left join (select a.school_id,NVL(teaNum,0) teaNum ,NVL(stuNum,0) stuNum,NVL(jfNum,0) jfNum from (select school_id ,count(userid) teaNum  from xj_teacher group by school_id )a left join (select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id)b on a.school_id = b.school_id left join (  select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from cs_xj_family c,cs_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from cs_xj_family c,cs_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id )c on a.school_id = c.school_id )brpt on crpt.school_id = brpt.school_id  )mrpt left join town on mrpt.town_id=town.id left join QX_ADMIN_SI on QX_ADMIN_SI.id=mrpt.sid
 select QX_ADMIN_SI.company SI,mrpt.* from ( select sid,count(school_id) schoolNum,sum(teaNum) teaNum,sum(stuNum)stuNum,sum(jfNum)jfNum,sum(alogs)alogs,sum(tlogs)tlogs,sum(dx_flow)dx_flow from( select crpt.*, brpt.teaNum,brpt.stuNum,brpt.jfNum from (  select xj_school.area_id,xj_school.school_name school,town_id,c.id sid,rpt.* from(  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 ) rpt  left join xj_school on xj_school.id = rpt.school_id  left join qx_user_school b on b.school_id = rpt.school_id   left join qx_admin_si c on c.id = b.user_id   left join qx_account_school d on d.school_id = rpt.school_id where d.user_id= 1 and town_id=3863 and school_id=1 and c.id=1674 )crpt left join (select a.school_id,NVL(teaNum,0) teaNum ,NVL(stuNum,0) stuNum,NVL(jfNum,0) jfNum from (select school_id ,count(userid) teaNum  from xj_teacher group by school_id )a left join (select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id)b on a.school_id = b.school_id left join (  select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from cs_xj_family c,cs_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from cs_xj_family c,cs_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id )c on a.school_id = c.school_id )brpt on crpt.school_id = brpt.school_id ) group by sid　)mrpt left join QX_ADMIN_SI on mrpt.sid=QX_ADMIN_SI.id　 

select * from QX_ADMIN_SI
select * from town
select * from user_tables where table_name like '%SI%'

