select * from cs_xj_student a

SELECT * FROM QX_ADMIN_USER

select * from user_tables
where table_name like '%USER%'

select * from cs_xj_family


 SELECT * FROM config_cache a LEFT JOIN area b ON a.area_id = b.ID
    LEFT JOIN qx_function c ON a.function_id = c.ID
       WHERE a.function_id > -1 AND UPPER (url) LIKE UPPER ('%stuqry.do%')
       
alter table cs_xj_family add ic_no varchar2(20);

alter table cs_xj_family add (ic_no varchar2(20));









select* from temp_insert_importstu
select * from temp_importstu
select * from t_importstu

insert into temp_insert_importstu 


select * from (select stu_sequence,stuname,stu_no,ic_no,ic_no_2,class_id,phone,phonetype,famname,
file_time,rank() over(partition by ti.stuname,ti.class_id order by stu_sequence) rn,
sex,bind_phone,fps_phone1,fps_phone2,fps_phone3,relationship,icard_no 
from temp_importstu ti 
where not exists(select 1 from cs_xj_student a,cs_xj_stu_class b, cs_xj_family c
 where a.stu_sequence=b.stu_sequence and c.stu_sequence=b.stu_sequence and ti.class_id=b.class_id 
 and ti.stuname=a.name  and b.school_id=1) and not exists (select 1 from blacklist bl
 where bl.phone=ti.phone) and ti.school_id=1 and ti.file_time='1284623747232' and ti.dealflag=0) where rn=1




