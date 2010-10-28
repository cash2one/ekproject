select town_name 县区,
            school_name 学校名称,
            sa,b.teacher_nums 教师数,
            c.student_nums 学生数,
            d.jf_nums 计费数,
            logon_teas 登录教师数,
            logon_nums 登录次数,
            sms_nums 短信发送量 from wz_xj_school a left  join 
(select count(*) teacher_nums,a.school_id  from xj_teacher a,wz_xj_school b where a.school_id=b.school_id group by a.school_id) b on a.school_id=b.school_id 
left join 
(select count(*) student_nums,a.school_id  from wz_xj_stu_class a,wz_xj_school b where a.school_id=b.school_id group by a.school_id) c  on a.school_id=c.school_id
 left join 
(select count(*) jf_nums,a.school_id from wz_xj_stu_class a,wz_xj_school b where a.school_id=b.school_id and
   exists(select 1 from wz_xj_family c,wz_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null) 
   group by a.school_id) d on d.school_id=a.school_id
 left join 
(select count(*) logon_teas,a.school_id from xj_teacher a,wz_xj_school b where a.school_id=b.school_id and
 exists (select 1 from wz_logs c where a.id=c.user_id and a.school_id=c.school_id and to_char(DT,'yyyy-mm-dd')>='2010-09-01' and type=9)
  group by a.school_id ) e on e.school_id=a.school_id 
left join
(select count(*) logon_nums,a.school_id from wz_logs a,wz_xj_school b where a.school_id=b.school_id and to_char(DT,'yyyy-mm-dd')>='2010-09-01' and type=9 
group by a.school_id ) f on f.school_id=a.school_id
 left join
(select count(*) sms_nums,a.school_id from wz_dx_sms a,wz_xj_school b where a.school_id=b.school_id and to_char(DT,'yyyy-mm-dd')>='2010-09-01'
 group by a.school_id) g on g.school_id=a.school_id


select count(*) teacher_nums,a.school_id  from xj_teacher a,xj_school b where a.school_id=b.id group by a.school_id order by school_id
select count(*),school_id from xj_teacher group by xj_teacher.school_id  order by school_id
select count(*) from xj_teacher
select count(*) from xj_teacher a,xj_school b where a.school_id=b.id 

select count(*) student_nums,school_id  from wz_xj_stu_class group by school_id
union all
select count(*) student_nums,school_id  from cs_xj_stu_class group by school_id


select * from wz_dx_sms
select * from wz_logs
select * from cs_school_use_state



drop table cs_school_use_state;
create table cs_school_use_state(
   id number(11),school_id  number(11),year number(6),
   "1_log" number(11) default 0 ,"1_log_teacher" number(11),"1_dx_flow" number(11),
   "2_log" number(11) default 0,"2_log_teacher" number(11),"2_dx_flow" number(11),
   "3_log" number(11) default 0,"3_log_teacher" number(11),  "3_dx_flow" number(11),
   "4_log" number(11) default 0, "4_log_teacher" number(11),"4_dx_flow" number(11),
   "5_log" number(11) default 0,"5_log_teacher" number(11),"5_dx_flow" number(11),
   "6_log" number(11) default 0, "6_log_teacher" number(11),"6_dx_flow" number(11),
   "7_log" number(11) default 0, "7_log_teacher" number(11), "7_dx_flow" number(11),
   "8_log" number(11) default 0,"8_log_teacher" number(11),"8_dx_flow" number(11),
   "9_log" number(11),"9_log_teacher" number(11),"9_dx_flow" number(11),
   "10_log" number(11),"10_log_teacher" number(11),"10_dx_flow" number(11),
   "11_log" number(11), "11_log_teacher" number(11),"11_dx_flow" number(11),
   "12_log" number(11), "12_log_teacher" number(11),"12_dx_flow" number(11)
)


create  sequence cs_school_use_seq_id   increment   by   1   start   with   1  maxvalue   999999999;


select * from qz_school_use_state
select * from cs_school_use_state
select * from hz_school_use_state
select * from jx_school_use_state
select * from nb_school_use_state
select * from sx_school_use_state
select * from tz_school_use_state
select * from wz_school_use_state
select * from ls_school_use_state
select * from jh_school_use_state
select * from zs_school_use_state


-- 验证sql
 select area.name area_name,mrpt.* from (
       select area_id,count(school_id) schoolNum,sum(teaNum) teaNum,sum(stuNum)stuNum,sum(jfNum)jfNum,
       sum(alogs)alogs,sum(tlogs)tlogs,sum(dx_flow)dx_flow	 
       from( 
         -- 组合数据
          select crpt.*, brpt.teaNum,brpt.stuNum,brpt.jfNum from ( 
          -- 基本数据 
              select a.school_id,NVL(teaNum,0) teaNum ,NVL(stuNum,0) stuNum,NVL(jfNum,0) jfNum 
                  from (
                   select school_id ,count(userid) teaNum  from xj_teacher
                   group by school_id )a 
          
                   left join (
                      
                        select school_id ,count(*) stuNum  
                        from cs_xj_stu_class group by school_id
                        
                     )b on a.school_id = b.school_id 
                     
                   left join (
                       select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b 
                       where a.id=b.id and 
                       exists(select 1 from cs_xj_family c,cs_tranpackage_customer d
                       where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null) 
                       group by a.school_id )c on a.school_id = c.school_id   
           )brpt 
           
     --crpt
     left join ( 
                 select xj_school.area_id,xj_school.school_name school,town_id,c.id sid,rpt.* from(
                      select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow 
                      from cs_school_use_state  where year=2010 
                     ) 
                  rpt  
                  left join xj_school on xj_school.id = rpt.school_id 
                  left join qx_user_school b on b.school_id = rpt.school_id  
                  left join qx_admin_si c on c.id = b.user_id  
                  left join qx_account_school d on d.school_id = rpt.school_id 
                  where d.user_id= 1 and town_id=3863 
           
           )crpt on crpt.school_id = brpt.school_id 
           
      ) 
   
      group by area_id )mrpt left join area on area.id= mrpt.area_id 

--


-- 修改

-- 验证sql

    select area_id,area_name,count(school_name) schoolNum,sum(teaNum) teaNum,sum(stuNum)stuNum,sum(jfNum)jfNum,
    sum(alogs)alogs,sum(tlogs)tlogs,sum(dx_flow)dx_flow	 
       from( 
             -- 组合数据
          select area.id area_id, area.name area_name,town.id town_id,town.name town_name,a.school_name,c.id si_id,c.company si,a.create_time create_time, brpt.school_id,brpt.teaNum,brpt.stuNum,brpt.jfNum,NVL(crpt.alogs,0)alogs,NVL(crpt.tlogs,0) tlogs,NVL(crpt.dx_flow,0) dx_flow from ( 
              -- 基本数据 
                  select xj_school.id school_id,NVL(teaNum,0) teaNum ,NVL(stuNum,0) stuNum,NVL(jfNum,0) jfNum 
                      from  xj_school left join(
                       select school_id ,count(userid) teaNum  from xj_teacher
                       group by school_id )a on a.school_id =  xj_school.id
              
                       left join (
                            select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id
                            
                         )b on  xj_school.id = b.school_id 
                         
                       left join (
                           select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b 
                           where a.id=b.id and 
                           exists(select 1 from cs_xj_family c,cs_tranpackage_customer d
                           where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null) 
                           group by a.school_id
                           
                           )
                           c on a.school_id = c.school_id   
               )brpt 
               
         --crpt
          left join (         
                     select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow 
                     from cs_school_use_state  where year=2010 
                   
                    )crpt on crpt.school_id = brpt.school_id 
          
         left join xj_school a on brpt.school_id = a.id 
         left join area on area.id = a.area_id 
         left join town on town.id = a.town_id
         left join qx_user_school b on b.school_id = brpt.school_id  
         left join qx_admin_si c on c.id = b.user_id  
         where town.id = 1 and b.user_id = 1 and area.id= 1 and c.id =1 and a.id = 1
         
       --  left join qx_account_school d on d.school_id = brpt.school_id 
         
      ) trpt
   
      group by trpt.area_name,area_id
      having area_name <> null or area_name <>'' 
      order by alogs,tlogs,dx_flow desc
      

--

 select distinct si,count(school_name) schoolNum,sum(teaNum) teaNum,sum(stuNum)stuNum,sum(jfNum)jfNum,sum(alogs)alogs,sum(tlogs)tlogs,sum(dx_flow)dx_flow from(select area.id area_id, area.name area_name,town.id town_id,town.name town_name,a.school_name,c.id si_id,c.company si,a.create_time create_time, brpt.school_id,brpt.teaNum,brpt.stuNum,brpt.jfNum,NVL(crpt.alogs,0)alogs,NVL(crpt.tlogs,0) tlogs,NVL(crpt.dx_flow,0) dx_flow from ( select xj_school.id school_id,NVL(teaNum,0) teaNum ,NVL(stuNum,0) stuNum,NVL(jfNum,0) jfNum from  xj_school left join (select school_id ,count(userid) teaNum  from xj_teacher group by school_id )a on a.school_id =  xj_school.id  left join (select school_id ,count(*) stuNum  from qz_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from hz_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from huz_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from jx_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from nb_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from sx_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from tz_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from wz_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from ls_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from jh_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from zs_xj_stu_class group by school_id union all select school_id ,count(*) stuNum  from cs_xj_stu_class group by school_id)b on  xj_school.id = b.school_id left join (  select a.school_id,count(*) jfNum from qz_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from qz_xj_family c,qz_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from hz_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from hz_xj_family c,hz_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from huz_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from huz_xj_family c,huz_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from jx_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from jx_xj_family c,jx_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from nb_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from nb_xj_family c,nb_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from sx_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from sx_xj_family c,sx_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from tz_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from tz_xj_family c,tz_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from wz_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from wz_xj_family c,wz_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from ls_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from ls_xj_family c,ls_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from jh_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from jh_xj_family c,jh_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from zs_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from zs_xj_family c,zs_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id  union all  select a.school_id,count(*) jfNum from cs_xj_stu_class a,xj_school b  where a.id=b.id and exists(select 1 from cs_xj_family c,cs_tranpackage_customer d where a.stu_sequence=c.stu_sequence and c.id=d.family_id and d.charge=1 and d.del=0 and d.boss_salemodalid is not null)  group by a.school_id )c on a.school_id = c.school_id )brpt left join ( select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  
 where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow
  from cs_school_use_state  where year=2010 union all  
  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow 
  from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 union all  select school_id,"10_log" alogs,"10_log_teacher" tlogs,"10_dx_flow" dx_flow from cs_school_use_state  where year=2010 )crpt 
  on crpt.school_id = brpt.school_id  left join xj_school a on brpt.school_id = a.id left join area 
  on area.id = a.area_id  left join town on town.id = a.town_id left join qx_user_school b
   on b.school_id = brpt.school_id  left join qx_admin_si c on c.id = b.user_id   where 1=1   
        ) group by si, si_id having  si is not null  order by alogs desc


  select * from xj_school school where school.area_id in(
   select id from area where name like '金华')
   
  delete from  jh_school_use_state
  select * from jh_school_use_state
  insert into jh_school_use_state 
  (id,school_id,year,"5_log","5_log_teacher","5_dx_flow")
  values(jh_school_use_seq_id.nextval,30,2010,100,10,5) 
  
 
select * from ( 
   select school_id,year,month, sum(type_charge),sum(stu_num)   from tranpackage_type_stat 
   group by  school_id,year,month
   order by school_id
 )

 
