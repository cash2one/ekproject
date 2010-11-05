SELECT   a.area_name 地区, a.si_name si名称, a.cnt "12月21日客户总人数",
         b.cnt "12月25日客户总人数", f_percent (b.cnt - a.cnt, a.cnt) 增长率
    FROM report2 a, report3　b
WHERE a.area_name = b.area_name AND a.si_name = b.si_name
ORDER BY a.area_name, a.si_name


-- SI增长率周统计表
create table si_increase_stat(
   id number(20),
   year number(6),
   week_seq number(6),
   si varchar(40),
   area varchar(40), 
   last_num number(6),
   current_num number(6),
   increase float(3)
)


create table si_activity_stat(
   id number(20),
   year number(6),
   week_seq number(6),
   si varchar(40),
   area varchar(40), 
   jz_num number(6),
   yd_cnt number(6),
   kt_cnt number(6),
   dx_cnt number(6),
   kt_percent float(3), 
   activity_percent float(3)
)


--SI 静态数据分析
create table si_staticdata_stat(
   id number(20),
   year number(6),
   week_seq number(6),
   si varchar(40),
   area varchar(40), 
   school_cnt number(6),
   stu_cnt number(6),
   sf_cnt number(6),
   fee number(6),
   area_id  number(3)
)




SELECT   a.area_name 地区, a.si_name si名称, a.jz_cnt 家长总数,
         a.yd_cnt 移动总人数, a.kt_cnt 开通家长短信箱人,
         b.cnt 接收过短信的人数, f_percent (a.kt_cnt, a.yd_cnt) 开通率,
         f_percent (DECODE (SIGN (NVL (b.cnt, 0) - a.kt_cnt),
                            -1, b.cnt,
                            a.kt_cnt
                           ),
                    a.kt_cnt
                   ) 活跃率
    FROM report4 a LEFT JOIN report5　b
         ON a.area_name = b.area_name AND a.si_name = b.si_name
ORDER BY a.area_name, a.si_name




SELECT   b.NAME, COUNT (DECODE (a.bank, 1, student_cnt, NULL)),
         SUM (DECODE (a.bank, 1, student_cnt, 0)) ,
         COUNT (DECODE (a.bank, 2, student_cnt, NULL)),
         SUM (DECODE (a.bank, 2, student_cnt, 0)),
         COUNT (DECODE (a.bank, 3, student_cnt, 5, student_cnt, NULL)),
         SUM (DECODE (a.bank, 3, student_cnt, 5, student_cnt, 0)),
         COUNT (DECODE (a.bank, 4, student_cnt, NULL)),
         SUM (DECODE (a.bank, 4, student_cnt, 0))
    FROM school_bank a, area b
   WHERE a.area_id = b.ID AND b.ID IN (6, 3, 1, 22, 5, 15, 26, 18)
GROUP BY b.NAME