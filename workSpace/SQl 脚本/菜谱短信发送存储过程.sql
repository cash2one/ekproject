CREATE OR REPLACE PROCEDURE ZJXXT."PROC_AUTO_SEND_RECIPE_MSG" (
request_ip varchar2,   -- ����� IP ��ַ
userid varchar2,  --�û��ʺ�
user_id varchar2, --�û�ID
job_id varchar2,  --��������
p_log_message1 varchar2,  --��־����1
p_log_message2 varchar2   --��־����2
)
--*****************************************
-- �Զ����Ͳ�����Ϣ�洢����
--*************************************8
IS
   p_sql varchar2(30000);                
   p_class_id number(8);
   p_teacher_id number(8);
   TYPE cursor_ref IS REF CURSOR;
   recipeInfo cursor_ref;  
   day_seq number(2);
   recipe_content varchar2(1000);
BEGIN

      --�ó��������ܼ�
      select case when to_char(sysdate,'d')='1' then 7 else to_number(to_char(sysdate,'d'))-1 end day into day_seq from dual;
         
      p_sql:=' select teacher_id,class_id,week'||day_seq||'_am||week'||day_seq||'_pm as content from recipe where auto_sendmsg = 1 and begin_date<=sysdate and end_date>=sysdate ';
   
      DBMS_OUTPUT.put_line(p_sql);

      open recipeInfo for p_sql;
      LOOP
         
         FETCH recipeInfo INTO p_teacher_id,p_class_id,recipe_content;
         exit when recipeInfo%notfound;
      
         SELECT * FROM      
         
           
         execute immediate p_sql;
       
      END LOOP;
      CLOSE recipeInfo; 
       
   END;




