      SELECT *
        FROM config_cache a LEFT JOIN area b ON a.area_id = b.ID
             LEFT JOIN qx_function c ON a.function_id = c.ID
       WHERE a.function_id > -1 AND UPPER (url) LIKE UPPER ('%mzsetoperationbysms.do%')


select fa.id,  fa.name ,fa .tswyflag   from zs_xj_family fa
where fa.name like '%新八家长%'  or  fa.name like '%余%' 


select * from  v_user 
where type = 'SI' and role_name like '梅州%'

/*  查询用户的套餐定制情况  */
select * from zs_package_confirmstatus_new  pc 
    where pc.family_id in (
    select fa.id from zs_xj_family fa
    where fa.name like '%elamF%'   
)

select * from zs_package_customer pc
where pc.family_id in (
    select fa.id from zs_xj_family fa
    where fa.name like '%elamF%'  
)

select * from zs_package_confirmstatus_new  pc 
where family_id in (
        select fa.id from zs_xj_family fa
    where fa.name like '%elamF%'   
)

select * from zs_xj_family 
where name   like '%elam%'  

select * from base_transaction bt
select * from package_basetran pb  left join base_transaction bt on pb.basetran_id = bt.id  where pb.package_id in(101)
select * from package_define where id in(401,101)

/*查询用户的套餐定制情况*/
select * from  zs_package_customer
where family_id = (
        select fa.id from zs_xj_family fa
    where fa.name like '%13826098962%'   
)

select * from area
where area.id=9
/*查询套餐*/
select * from package_define
where id =242

select * from package_define
where name like '安全平台%'

/*基本业务表*/
select * from base_transaction bt
where bt.relate_tran = 7

/*营销方案业务对应表*/
select * from package_basetran pb  left join base_transaction bt  on bt.relate_tran = pb.basetran_id
where pb.package_id = 161
order by  pb.package_id



select * from  handle_business_set

select *  from area



/* 短信*/
select * from dx_groupsend  where object_mobile like '13826098916'  order by create_time desc

select * from zs_transaction_log order by open_date desc
delete from dx_groupsend  where object_mobile = '15917292802'

/* 查询开通业务*/
select * from mz_xj_family  fa where fa.name like 'Felam3'  
select  *  from mz_package_customer where del=0 and family_id=(select  fa.id  from mz_xj_family fa where fa.name like 'Felam3')
select count(*) from mz_tran_klsynlesson kl where kl.family_del = 0 and chg_flag =1 and kl.family_id =(select  fa.id  from mz_xj_family fa where fa.name like 'Felam3')
select count(*) from mz_tran_synlesson kl where kl.family_del = 0 and chg_flag =1 and kl.family_id =(select  fa.id  from mz_xj_family fa where fa.name like 'Felam3')
select * from mz_package_confirmstatus_new pcn where pcn.family_id  =(select  fa.id  from mz_xj_family fa where fa.name like 'Felam3')



/* 删除业务*/
delete from   zs_package_customer where del=0 and family_id=(select  fa.id  from zs_xj_family fa where fa.name like 'Felam3')

<<<<<<< .mine
select fa.is_dxx,fa.is_kaoqin,fa.is_liuyanban,fa.is_qin_qing_tel,fa.tswyflag,fa.ty  from zs_xj_family fa where fa.name like '13826098962'

select * from dx_groupsend  where object_mobile like '13826098917'  order by create_time desc

--package_define
select * from package_define
=======
select fa.is_dxx,fa.is_kaoqin,fa.is_liuyanban,fa.is_qin_qing_tel  from zs_xj_family fa where fa.name like 'nef'

select * from zs_xj_student  stu where stu.name = 'nelam'


select * from dx_groupsend  where object_mobile like '13826098916'  order by create_time desc
>>>>>>> .r46
