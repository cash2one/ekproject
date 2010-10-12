CREATE OR REPLACE PROCEDURE PRO_STAT_DX_LOG_EVERYDAY_TEST IS
p_year number;  
p_month number;  
p_day number;    
p_area varchar2(10);
sqlStr varchar2(400);
t_time varchar(20);
cursor c_area is select abb from area;
BEGIN
    p_year:=to_number(to_char(sysdate,'yyyy') );
    p_month:=to_number(to_char(sysdate,'mm'));
    t_time:=to_char(sysdate,'yyyy-mm');
  

    for v_abb in c_area loop
        p_area:=v_abb.abb;
     
     -- dbms_output.put_line(p_area); 
    
      --统计当月的登录（教师）情况、短信流量     
          sqlStr:='select count(*) logon_teas,a.school_id from xj_teacher a, xj_school b' 
              ||'  where a.school_id=b.id and exists (select 1 from '||p_area||'_logs c  where a.id=c.user_id and a.school_id=c.school_id and '
              ||' to_char(DT,''yyyy-mm'')='''||t_time||'''  and type=9) ' 
              ||' group by a.school_id' ;
         -- execute immediate sqlStr;
         dbms_output.put_line(sqlStr); 
      

       cursor log_cursor is   sqlStr;
        
        for  temp_cursor  in log_cursor loop
             dbms_output.put_line('教师登录情况:学校ID  '||temp_cursor.school_id||'登录数'||temp_cursor.logon_teas); 
         end loop;
          
          
         --统计当月的登录情况     
          sqlStr:='select count(*) logon_nums,a.school_id from  '||p_area||'_logs a,xj_school b '
          ||' where a.school_id=b.id and to_char(DT,''yyyy-mm'')='''||t_time||'''  and type=9 group by a.school_id ';
          dbms_output.put_line(sqlStr);    
          
          
         -- 短信流量   
           sqlStr:='select count(*) sms_nums,a.school_id from  '||p_area||'_dx_sms a,xj_school b '
          ||' where a.school_id=b.id and to_char(DT,''yyyy-mm'')='''||t_time||''' group by a.school_id ';
          
        -- execute immediate sqlStr;
        dbms_output.put_line(sqlStr); 
     
    end loop;
    
END PRO_STAT_DX_LOG_EVERYDAY_TEST; 



