select area_id,count(school_id) jf_school,year,month from tranpackage_type_stat
where TYPE_CHARGE>0
group by area_id,type_id,year,month
having year=2010 and month=8
order by area_id


select area_id,year,month from tranpackage_type_stat
where TYPE_CHARGE>0
group by area_id,type_id,year,month
having year=2010 and month=8
order by area_id



select * from tranpackage_type_stat
where TYPE_CHARGE>0 and
area_id=1 and year=2010 and month = 8
order by area_id


update tranpackage_type_stat
set school_id = 22,type_id=2,
where id = 11120


select area_id,school_id from tranpackage_type_stat
where TYPE_CHARGE>0 and area_id=1 and year=2010 and month = 8
group by area_id,school_id
order by area_id



select area_id,count(school_id) jf_school from (
    select a.* from 
     (  select area_id,school_id from tranpackage_type_stat
        where TYPE_CHARGE>0 and year=2010 and month = 8
        group by area_id,school_id
        order by area_id ) a 
    left join xj_school b on a.school_id=b.id 
    left join town c on b.town_id=c.id  
    left join qx_user_school d on a.school_id=d.school_id
    left join qx_admin_si e on e.id=d.user_id 
    left join qx_account_school qxs on a.school_id=qxs.school_id
    where qxs.user_id= 1
) t group by area_id  order by area_id    