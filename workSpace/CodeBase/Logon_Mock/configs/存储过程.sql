/* Formatted on 2010/12/01 13:54 (Formatter Plus v4.8.8) */
CREATE OR REPLACE PROCEDURE pro_xxt_synless_login (p_info OUT number)
IS
BEGIN

   DBMS_OUTPUT.put_line ('Start Run PRO_XXT_SYNLESS_LOGIN.....');
   p_info := 100;

   DELETE FROM synlesson_logon_temp;


   --��ɶ�Ӧ�����
   INSERT INTO synlesson_logon_temp
               (ID, stu_sequence, syn_tran)
      SELECT   rownum, stu_sequence,
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
                                       WHERE is_login = 0
                                    ORDER BY stu_sequence, TYPE) a
                        CONNECT BY stu_sequence = PRIOR stu_sequence
                               AND x - 1 = PRIOR x))
         WHERE rn = 1
      ORDER BY stu_sequence;

   COMMIT;
   
   
   
   p_info := 100;
   DBMS_OUTPUT.put_line ('Run PRO_XXT_SYNLESS_LOGIN Finished!');
   
   exception 
   when others then  
   p_info := 200;
   DBMS_OUTPUT.put_line ('Run PRO_XXT_SYNLESS_LOGIN With Error!');

END pro_xxt_synless_login;