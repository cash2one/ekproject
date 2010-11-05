CREATE OR REPLACE PROCEDURE GDXXT."PRO_XXT_SIREPORT_LOG" IS
tmpVar          NUMBER;
p_year          number;
p_week          number;
p_today         varchar2(10);
P_SQL           VARCHAR2(2000);
p_sum           number;
type cursor_ref is ref cursor;
c_comment cursor_ref;
BEGIN
    
     
   select to_char(sysdate,'day','NLS_DATE_LANGUAGE = American') into p_today from dual; 
    
   select to_number(to_char(sysdate,'yyyy')) into p_year from dual;
              
   select to_number(to_char(sysdate,'WW')) into p_week from dual; 
           
     
      dbms_output.put_line(p_today); 
      IF(p_today='friday') THEN
       
         
           -- 插入 SI 增长率统计表
           P_SQL:='insert into si_increase_stat(id,year,week_seq,area,si,current_num,last_num,increase)';
           P_SQL:=P_SQL||' SELECT 1,'||p_year||','||p_week||','||' a.area_name, a.si_name si, a.cnt,b.cnt ,f_percent (b.cnt - a.cnt, a.cnt) ';
           P_SQL:=P_SQL||' FROM report2 a, report3　b WHERE a.area_name = b.area_name AND a.si_name = b.si_name ';

          -- dbms_output.put_line(p_sql); 
           execute immediate p_sql;
           
          
          --SI 活跃率周统计表
         
          P_SQL:='insert into si_activity_stat(id,year,week_seq,area,si,jz_num,yd_cnt,kt_cnt,dx_cnt,kt_percent,activity_percent)';
          P_SQL:=P_SQL||' SELECT 1,'||p_year||','||p_week||','||' a.area_name,a.si_name si,a.jz_cnt,a.yd_cnt,a.kt_cnt,b.cnt,f_percent (a.kt_cnt, a.yd_cnt),';
          P_SQL:=P_SQL||' f_percent (DECODE (SIGN (NVL (b.cnt, 0) - a.kt_cnt), -1, b.cnt, a.kt_cnt), a.kt_cnt)';
          P_SQL:=P_SQL||' FROM report4 a LEFT JOIN report5　b ON a.area_name = b.area_name AND a.si_name = b.si_name ';
         
         -- dbms_output.put_line(p_sql); 
          execute immediate p_sql;
       
          
          
      END IF;

END PRO_XXT_SIREPORT_LOG;
