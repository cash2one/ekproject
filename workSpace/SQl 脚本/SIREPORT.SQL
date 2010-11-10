CREATE OR REPLACE PROCEDURE "PRO_XXT_SIREPORT_LOG" IS
tmpVar          NUMBER;
p_year          number;
p_area          varchar2(10);
p_week          number;
p_today         varchar2(10);
P_SQL           VARCHAR2(2000);
p_sum           number;
cursor c_area is select abb from area;
BEGIN
    
   select to_char(sysdate,'day','NLS_DATE_LANGUAGE = American') into p_today from dual; 
    
   select to_number(to_char(sysdate,'yyyy')) into p_year from dual;
              
   select to_number(to_char(sysdate,'WW')) into p_week from dual; 
           
     
    --dbms_output.put_line(p_today);
     IF instr(p_today,'monday') >0  THEN
          dbms_output.put_line('Start Run PRO_XXT_SIREPORT_LOG '); 
           -- ?? SI ??????
           P_SQL:='insert into si_increase_stat(year,week_seq,area,si,current_num,last_num,increase)';
           P_SQL:=P_SQL||' SELECT '||p_year||','||p_week||','||' a.area_name, a.si_name si, a.cnt,b.cnt ,f_percent (b.cnt - a.cnt, a.cnt) ';
           P_SQL:=P_SQL||' FROM report2 a, report3 b WHERE a.area_name = b.area_name AND a.si_name = b.si_name ';

          -- dbms_output.put_line(p_sql); 
           execute immediate p_sql;
           
          
          --SI ???????
         
          P_SQL:='insert into si_activity_stat(year,week_seq,area,si,jz_num,yd_cnt,kt_cnt,dx_cnt,kt_percent,activity_percent)';
          P_SQL:=P_SQL||' SELECT '||p_year||','||p_week||','||' a.area_name,a.si_name si,a.jz_cnt,a.yd_cnt,a.kt_cnt,b.cnt,f_percent (a.kt_cnt, a.yd_cnt),';
          P_SQL:=P_SQL||' f_percent (DECODE (SIGN (NVL (b.cnt, 0) - a.kt_cnt), -1, b.cnt, a.kt_cnt), a.kt_cnt)';
          P_SQL:=P_SQL||' FROM report4 a LEFT JOIN report5 b ON a.area_name = b.area_name AND a.si_name = b.si_name ';
         
        -- dbms_output.put_line(p_sql); 
          execute immediate p_sql;
       
         
         --SI?????????
         
         delete report_si_temp1;
         
        
         for v_abb in c_area loop         
            p_area:=v_abb.abb;    
            
            
          -- ????????  
          P_SQL:='insert into report_si_temp1(school_id,bank,stu_num)'
           ||' SELECT c.id school_id,1,count(*) stu_num FROM '||p_area||'_XJ_STU_CLASS A '
           ||' LEFT JOIN XJ_CLASS B ON A.CLASS_ID = B.ID  and A.school_id = B.SCHOOL_ID'
           ||' LEFT JOIN XJ_SCHOOL C  ON A.SCHOOL_ID=C.ID and b.school_id = C.ID '
           ||' Left JOIN AREA D ON D.ID = C.AREA_ID '
           ||' WHERE B.CLASS_TYPE = 1 and (B.class_name like ''%??%'' or c.school_name like ''%??%'' or c.school_name like ''%???%'')' 
           ||' and not exists(select 1 from report_si_temp1 t where  t.school_id = c.id and t.bank=2 ) group by c.id'; 
                    
           execute immediate p_sql;     
            
            
          -- ??????  
          P_SQL:='insert into report_si_temp1(school_id,bank,stu_num)'
           ||' SELECT c.id school_id,2,count(*) stu_num FROM '||p_area||'_XJ_STU_CLASS A '
           ||' LEFT JOIN XJ_CLASS B ON A.CLASS_ID = B.ID  and A.school_id = B.SCHOOL_ID'
           ||' LEFT JOIN XJ_SCHOOL C  ON A.SCHOOL_ID=C.ID and b.school_id = C.ID '
           ||' Left JOIN AREA D ON D.ID = C.AREA_ID '
           ||' WHERE B.CLASS_TYPE = 1 and (B.class_name like ''%??%'' OR B.class_name like ''%?%'' or c.school_name like ''%??%'')' 
           ||' and not exists(select 1 from report_si_temp1 t where  t.school_id = c.id and t.bank=2 ) group by c.id'; 
                    
           execute immediate p_sql;  
           
           
          -- ??????  
          P_SQL:='insert into report_si_temp1(school_id,bank,stu_num)'
           ||' SELECT c.id school_id,3,count(*) stu_num FROM '||p_area||'_XJ_STU_CLASS A '
           ||' LEFT JOIN XJ_CLASS B ON A.CLASS_ID = B.ID  and A.school_id = B.SCHOOL_ID'
           ||' LEFT JOIN XJ_SCHOOL C  ON A.SCHOOL_ID=C.ID and b.school_id = C.ID '
           ||' Left JOIN AREA D ON D.ID = C.AREA_ID '
           ||' WHERE B.CLASS_TYPE = 1 and (B.class_name like ''%?%'' or B.class_name like ''%?%'' OR B.class_name like ''%??%'' or c.school_name like ''%??%'')' 
           ||' and not exists(select 1 from report_si_temp1 t where  t.school_id = c.id and t.bank=2 ) group by c.id'; 
                    
           execute immediate p_sql; 
         
           
           /*
           -- ??????
            P_SQL:='insert into report_si_temp1(school_id,bank,stu_num)'
           ||' SELECT c.id school_id,4,count(*) stu_num FROM '||p_area||'_XJ_STU_CLASS A '
           ||' LEFT JOIN XJ_CLASS B ON A.CLASS_ID = B.ID  and A.school_id = B.SCHOOL_ID'
           ||' LEFT JOIN XJ_SCHOOL C  ON A.SCHOOL_ID=C.ID and b.school_id = C.ID '
           ||' Left JOIN AREA D ON D.ID = C.AREA_ID '
           ||' WHERE B.CLASS_TYPE = 1 and (B.class_name like ''%?%'' or B.class_name like ''%?%'' OR B.class_name like ''%??%'' or c.school_name like ''%??%'')' 
           ||' and not exists(select 1 from report_si_temp1 t where  t.school_id = c.id and t.bank=2 ) group by c.id'; 
                    
             execute immediate p_sql; 
           */
         end loop; 
         
         
       -- ?????
         
         P_SQL:='INSERT INTO  SI_BANK_USER_STAT(year,week_seq,area,si,y_school_num,y_stu_num,y_stu_cnt'
         ||',x_school_num,x_stu_num,x_stu_cnt,z_school_num,z_stu_num,z_stu_cnt'
         ||',g_school_num,g_stu_num,g_stu_cnt) '
         ||' select '||p_year||','||p_week||','||' AREA.NAME,QAS.COMPANY,'
         ||' COUNT (DECODE (a.bank, 1, student_cnt, NULL)) Y_SCHOOL_NUM,'
         ||' SUM (DECODE (t.bank, 1, stu_num, 0)) Y_STU_NUM,'
         ||' SUM (DECODE (a.bank, 1, student_cnt, 0)) Y_STU_CNT,'
         ||' COUNT (DECODE (a.bank, 2, student_cnt, NULL)) X_SCHOOL_NUM,'
         ||' SUM (DECODE (t.bank, 2, stu_num, 0)) X_STU_NUM,'
         ||' SUM (DECODE (a.bank, 2, student_cnt, 0)) X_STU_CNT,'
         ||' COUNT (DECODE (a.bank, 3, student_cnt, 5, student_cnt, NULL)) Z_SCHOOL_NUM,'
         ||' SUM (DECODE (t.bank, 3, stu_num, 0)) Z_STU_NUM,'
         ||' SUM (DECODE (a.bank, 3, student_cnt, 5, student_cnt, 0)) Z_STU_CNT,'
         ||' COUNT (DECODE (a.bank, 4, student_cnt, NULL)) G_SCHOOL_CNT,'
         ||' SUM (DECODE (t.bank, 4, stu_num, 0)) G_STU_NUM,'
         ||' SUM (DECODE (a.bank, 4, student_cnt, 0)) G_STU_CNT'
         ||' from school_bank A'
         ||' left join report_si_temp1 t on t.school_id = A.school_id and A.bank = t.bank '
         ||' left join area on area.id = A.area_id '
         ||' left join qx_user_school QUS on QUS.school_id = A.SCHOOL_ID'
         ||' left join qx_admin_si QAS on QUS.USER_ID = QAS.ID'
         ||' group by AREA.NAME,QAS.COMPANY';
         
        -- dbms_output.put_line(p_sql); 
         execute immediate p_sql; 
         

         --SI??????????
         P_SQL:='INSERT INTO si_staticdata_stat (year,week_seq,si,school_cnt,stu_cnt,sf_cnt,fee,area_id)'
                 ||'select '||p_year||','||p_week||','||'t.si_name,t.school_cnt,t.student_cnt,t.sf_parent,t.fee,t.area_id  from report10 t'; 
         
         --dbms_output.put_line(p_sql); 
         execute immediate p_sql; 
         dbms_output.put_line('Run PRO_XXT_SIREPORT_LOG Finished!'); 
    END IF;

END PRO_XXT_SIREPORT_LOG;
