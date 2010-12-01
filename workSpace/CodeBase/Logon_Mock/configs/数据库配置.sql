-- 源数据
create table temp_syn_login1130 
(
    type    varchar2(100 byte),
    userid   varchar2(100 byte),
    stu_sequence   varchar2(100 byte),
    is_login   varchar2(100 byte)
)

--中间表数据  id | stu_sequence | user_id | pwd | syn_tran

drop table synlesson_logon_temp;

create table synlesson_logon_temp(
    id number(6),
    stu_sequence varchar2(20),
    syn_tran varchar2(20)
)


select * from temp_syn_login1130

select * from synlesson_logon_temp


--- 向临时表中插入数据
insert into synlesson_logon_temp(id,stu_sequence,syn_tran)
SELECT  rownum, stu_sequence,
         TRANSLATE (LTRIM (TYPE, '/'), '*/', '*,') syn_type
    FROM (SELECT ROW_NUMBER () OVER (PARTITION BY stu_sequence ORDER BY stu_sequence,
                  lvl DESC) rn,
                 stu_sequence, TYPE
            FROM (SELECT     stu_sequence, LEVEL lvl,
                             SYS_CONNECT_BY_PATH (TYPE, '/') TYPE
                        FROM (SELECT   stu_sequence, TYPE AS TYPE,
                                       ROW_NUMBER () OVER (PARTITION BY stu_sequence ORDER BY stu_sequence,
                                        TYPE) x
                                  FROM temp_syn_login1130
                                  where is_login = 0
                              ORDER BY stu_sequence, TYPE) a
                  CONNECT BY stu_sequence = PRIOR stu_sequence
                             AND x - 1 = PRIOR x))
   WHERE rn = 1
ORDER BY stu_sequence




select * from zs_child

select * from synlesson_logon_temp


select count(*) from (SELECT COUNT(*) FROM ) a




insert into synlesson_logon_temp


select a.*,rownum from temp_syn_login1130 a


---向源数据表插入数据
insert into temp_syn_login1130(type,userid,stu_sequence,is_login)
select 'zx',a.userid,a.stu_sequence,0 from zs_child a


select * from synlesson_logon_temp