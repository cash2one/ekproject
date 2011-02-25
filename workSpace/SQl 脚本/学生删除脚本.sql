CREATE OR REPLACE PROCEDURE ZJXXT."PROC_STU_DEL" (
request_ip varchar2,   -- ����� IP ��ַ
userid varchar2,  --�û��ʺ�
user_id varchar2, --�û�ID
job_id varchar2,  --��������
p_log_message1 varchar2,  --��־����1
p_log_message2 varchar2   --��־����2
)
--*****************************************
-- ѧ��ɾ���� ������
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

         -- 1 ɾ��ѧУѧ���ʺż�ͥ��ϵ��
         p_sql:=' delete from '||p_area_abb||'_parent_family a where exists(select 1 from xj_stu_sequence c,'||p_area_abb||'_xj_family '||
                '  b where c.stu_sequence=b.stu_sequence and b.school_id='||p_school_id||' and c.school_id='||p_school_id||' and a.family_id=b.id)';
          
         execute immediate p_sql;
        
         --DBMS_OUTPUT.put_line(p_sql);
        
      
         -- 2 ɾ��ѧУѧ���ʺű�
         p_sql:='delete from '||p_area_abb||'_parent a where exists(select 1 from '||p_area_abb||'_parent  b left join '||p_area_abb||'_parent_family'
               ||' c on b.id=c.parent_id where c.id is null and  a.id=b.id )';
          
         execute immediate p_sql;
             
          --DBMS_OUTPUT.put_line(p_sql);
         
         --3 ɾ���ɼ���Ϣ
         p_sql:='delete from '||p_area_abb||'_exam_score  a where exists(select 1 from xj_stu_sequence b  '
               ||' where a.stu_sequence=b.stu_sequence and b.school_id='||p_school_id||')';
         execute immediate p_sql;
        
          --DBMS_OUTPUT.put_line(p_sql);
      
        --4  ɾ��ѧ���춯��Ϣ��Ϣ
         p_sql:='delete from xj_grad   a where exists(select 1 from xj_stu_sequence b '
               ||' where a.stu_sequence=b.stu_sequence and b.school_id='||p_school_id||')';
         execute immediate p_sql;
         
       
          --DBMS_OUTPUT.put_line(p_sql);
       
       
         --5  ɾ��ѧ����ͥ�����Ϣ
         p_sql:='delete from  '||p_area_abb||'_xj_family   a where exists(select 1 from xj_stu_sequence b '
               ||' where a.stu_sequence=b.stu_sequence and b.school_id='||p_school_id||')';
         
         execute immediate p_sql;
         
          --DBMS_OUTPUT.put_line(p_sql);
           
         --6 ɾ��ѧ���༶��ϵ��Ϣ
         
         p_sql:='delete from  '||p_area_abb||'_xj_stu_class  a where exists(select 1 from xj_stu_sequence b'
               ||' where a.stu_sequence=b.stu_sequence and b.school_id='||p_school_id||')';
         execute immediate p_sql;
         
         
          --DBMS_OUTPUT.put_line(p_sql);
         
         --/ 7 ɾ��ѧ��������Ϣ
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

