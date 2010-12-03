--- 查询后台用户
select * from qx_admin_user a
where exists (select * from qx_admin_role b where a.role_id = b.id and b.type_id = 1)
and userid like 'admin%'


--- 查询移动后台用户
select * from qx_admin_user a
where exists (select * from qx_admin_role b where a.role_id = b.id and b.type_id = 2)
and userid like '%gzchen%'

--- 查询SI 用户
select * from qx_admin_user a
where exists (select * from qx_admin_role b where a.role_id = b.id and b.type_id = 3)
and userid like 'zs%'



--查询功能名称对应的 代码连接

SELECT * from config_cache a
where exists ( 
      select * from qx_function b where a.function_id = b.id 
      and b.name like '%客服服务定制%'      
)



SELECT * from config_cache a
where exists ( 
      select * from qx_function b where a.function_id = b.id 
      and b.url like '%whbook%'     
)


select * from user_tables
where table_name like '%CPM%C%'



select * from CZ_CPMESSAGE


select * from xj_teacher
where userid like 'zsluhehai1'




select * from yw_tran_operator
order by create_time desc


select a. from gz_xj_family a
where exists 
 ( 
   select * from gz_xj_student b
   where a.stu_sequence = b.stu_sequence
   and b.name like '测试23'
 )
 
 


 update yw_tran_import a set type=4 
 where exists (select 1 from gz_blocklist b 
                 where b.phone=a.phone and b.del=0 and b.service in()) and operator_id=17531
                 
                 
                 
 select * from gz_blocklist                 
 
 
 select * from gz_tran_confirmstatus