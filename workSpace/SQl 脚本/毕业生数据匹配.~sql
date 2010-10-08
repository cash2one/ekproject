select * from cs_xj_graduate

select * from  cs_xj_student

select * from xj_school



/*地区 学校 数据*/
select  area.name, xs.id,xs.school_name,xc.id,xc.class_name  from cs_xj_stu_class  sc
 join xj_school xs on sc.school_id = xs.id 
  join xj_class xc on xc.id = sc.class_id and xc.school_id = xs.id
  join area on xs.area_id = area.id
  
  
  
  /*查询学生在校情况*/
  select st.stu_sequence,st.name,xs.school_name,xc.school_id ,xc.class_id,fa.phone from cs_xj_student st 
  join cs_xj_stu_class xc on xc.stu_sequence = st.stu_sequence 
  join xj_school xs on xs.id = xc.school_id
  join cs_xj_family fa on fa.stu_sequence = st.stu_sequence
  where ( st.name ='杨高岭' or st.name='乔鹏' or st.name='匡嫚' )and xs.school_name  like '浙江移动测试学校'
  
  select max(id) from cs_xj_stu_class
  
  
  /*加入学生就读信息*/
  insert into  cs_xj_stu_class(school_id,class_id,stu_sequence) values( );
  select * from cs_xj_stu_class
  
  
  /*精确匹配毕业生数据*/
  select sc.school_name, gr.name,cl.class_name,fam.phone,fam.is_dxx,fam.is_kaoqin,fam.is_liuyanban,fam.is_qin_qing_tel  from cs_xj_graduate gr 
  left join xj_school sc on gr.school_id=sc.id
  left join xj_class cl on gr.school_id=cl.school_id  and gr.class_id=cl.id
  left join cs_xj_stu_class stu_cl on stu_cl.school_id=cl.school_id and stu_cl.class_id=cl.id and stu_cl.stu_sequence =gr.stu_sequence
  left join cs_xj_family fam on gr.stu_sequence=fam.stu_sequence  
  where fam.phone=15913349265 and gr.name='杨高岭' and sc.area_id=8
  
  
 select * from cs_xj_graduate
