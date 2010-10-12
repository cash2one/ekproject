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


select * from wz_dx_sms
select * from wz_logs
select * from cs_school_use_state




create table cs_school_use_state(
   id number(11),school_id  number(11),year number(6),
   "1_log" number(11),"1_log_teacher" number(11),"1_dx_flow" number(11),
   "2_log" number(11),"2_log_teacher" number(11),"2_dx_flow" number(11),
   "3_log" number(11),"3_log_teacher" number(11),  "3_dx_flow" number(11),
   "4_log" number(11), "4_log_teacher" number(11),"4_dx_flow" number(11),
   "5_log" number(11),"5_log_teacher" number(11),"5_dx_flow" number(11),
   "6_log" number(11), "6_log_teacher" number(11),"6_dx_flow" number(11),
   "7_log" number(11), "7_log_teacher" number(11), "7_dx_flow" number(11),
   "8_log" number(11),"8_log_teacher" number(11),"8_dx_flow" number(11),
   "9_log" number(11),"9_log_teacher" number(11),"9_dx_flow" number(11),
   "10_log" number(11),"10_log_teacher" number(11),"10_dx_flow" number(11),
   "11_log" number(11), "11_log_teacher" number(11),"11_dx_flow" number(11),
   "12_log" number(11), "12_log_teacher" number(11),"12_dx_flow" number(11)
)



select count(*) logon_teas,a.school_id from xj_teacher a, xj_school b  where a.school_id=b.id and exists (select 1 from cs_logs c  where a.id=c.user_id and a.school_id=c.school_id and  to_char(DT,'yyyy-mm')='2010-05'  and type=9)  group by a.school_id
select count(*) logon_nums,a.school_id from  cs_logs a,xj_school b  where a.school_id=b.id and to_char(DT,'yyyy-mm')='2010-05'  and type=9 group by a.school_id 
select count(*) sms_nums,a.school_id from  cs_dx_sms a,xj_school b  where a.school_id=b.id and to_char(DT,'yyyy-mm')='2010-05' group by a.school_id 



