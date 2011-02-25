CREATE OR REPLACE PROCEDURE ZJXXT."PROC_STU_DEL" (
request_ip varchar2,   -- 请求的 IP 地址
userid varchar2,  --用户帐号
user_id varchar2, --用户ID
job_id varchar2,  --批处理编号
p_log_message1 varchar2,  --日志内容1
p_log_message2 varchar2   --日志内容2
)
--*****************************************
-- 学生删除的 任务处理
--*************************************8
IS
   p_sql varchar2(30000);                
   p_stu_sequence varchar2(30);
   p_school_id number(10);
   p_area_abb varchar2(8);
   TYPE cursor_ref IS REF CURSOR;
   stuinfo cursor_ref;

BEGIN
   
      p_sql:='select abb,stu_sequence,school_id from xj_stu_sequence where job_id='||job_id;
   
      open stuinfo for p_sql;
      
      LOOP
         
         FETCH stuinfo INTO p_area_abb,p_stu_sequence,p_school_id;
         exit when stuinfo%notfound;
      
         --DBMS_OUTPUT.put_line('delete list '||p_area_abb||' : '||p_stu_sequence||' : '||p_school_id||'  _job_id: '||job_id); 

         -- 1 删除学校学生帐号家庭关系表
         p_sql:=' delete from '||p_area_abb||'_parent_family a where exists(select 1 from xj_stu_sequence c,'||p_area_abb||'_xj_family '||
                '  b where c.stu_sequence=b.stu_sequence and b.school_id='||p_school_id||' and c.school_id='||p_school_id||' and a.family_id=b.id)';
          
         execute immediate p_sql;
        
         --DBMS_OUTPUT.put_line(p_sql);
        
      
         -- 2 删除学校学生帐号表
         p_sql:='delete from '||p_area_abb||'_parent a where exists(select 1 from '||p_area_abb||'_parent  b left join '||p_area_abb||'_parent_family'
               ||' c on b.id=c.parent_id where c.id is null and  a.id=b.id )';
          
         execute immediate p_sql;
             
          --DBMS_OUTPUT.put_line(p_sql);
         
         --3 删除成绩信息
         p_sql:='delete from '||p_area_abb||'_exam_score  a where exists(select 1 from xj_stu_sequence b  '
               ||' where a.stu_sequence=b.stu_sequence and b.school_id='||p_school_id||')';
         execute immediate p_sql;
        
          --DBMS_OUTPUT.put_line(p_sql);
      
        --4  删除学生异动信息信息
         p_sql:='delete from xj_grad   a where exists(select 1 from xj_stu_sequence b '
               ||' where a.stu_sequence=b.stu_sequence and b.school_id='||p_school_id||')';
         execute immediate p_sql;
         
       
          --DBMS_OUTPUT.put_line(p_sql);
       
       
         --5  删除学生家庭情况信息
         p_sql:='delete from  '||p_area_abb||'_xj_family   a where exists(select 1 from xj_stu_sequence b '
               ||' where a.stu_sequence=b.stu_sequence and b.school_id='||p_school_id||')';
         
         execute immediate p_sql;
         
          --DBMS_OUTPUT.put_line(p_sql);
           
         --6 删除学生班级关系信息
         
         p_sql:='delete from  '||p_area_abb||'_xj_stu_class  a where exists(select 1 from xj_stu_sequence b'
               ||' where a.stu_sequence=b.stu_sequence and b.school_id='||p_school_id||')';
         execute immediate p_sql;
         
         
          --DBMS_OUTPUT.put_line(p_sql);
         
         --/ 7 删除学生基本信息
          p_sql:='delete from  '||p_area_abb||'_xj_student   a where exists(select 1 from xj_stu_sequence b '
               ||'  where a.stu_sequence=b.stu_sequence and b.school_id='||p_school_id||')';
               
          execute immediate p_sql;
         
         
         --DBMS_OUTPUT.put_line(p_sql);
        
         commit;
         
      END LOOP;    
      CLOSE stuinfo;
      
        
      p_sql:='insert into admin_logs(userid,user_id,ip,operate1,operate2,type,dt)'
                ||' values('''||userid||''','||user_id||','''||request_ip||''','''||p_log_message1||''','''||p_log_message2||''',6,sysdate)';
         
      execute immediate p_sql;
     
      p_sql :=' update xj_stu_sequence set state = 1 where job_id='||job_id;
      execute immediate p_sql;
        
      commit;
      
   END;

