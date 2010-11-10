--- 查询后台用户
select * from qx_admin_user a
where exists (select * from qx_admin_role b where a.role_id = b.id and b.type_id = 1)
and userid like 'admin%'


--- 查询SI 用户
select * from qx_admin_user a
where exists (select * from qx_admin_role b where a.role_id = b.id and b.type_id = 3)
and userid like 'zs%'



--查询功能名称对应的 代码连接

SELECT * from config_cache a
where exists ( 
      select * from qx_function b where a.function_id = b.id 
      and b.name like '%客服受理统计表%'      
)


