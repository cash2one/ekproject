PL/SQL Developer Test script 3.0
26
declare 
  cursor c_area is select abb from area;
  tableName varchar(30);sqlStr varchar2(2000);
begin
   for v_abb in c_area loop
     tableName:=v_abb.abb||'_school_use_state';
    -- dbms_output.put_line('execute immediate: alter table '||tableName|| ' add icard_no varchar2(20)'); 
     sqlStr:= 'create table'||tableName||'('|| 'id number(11),school_id  number(11),year number(6),'||' "1_log" number(11) default 0 ,"1_log_teacher" number(11),"1_dx_flow" number(11),'||
    ' "2_log" number(11) default 0,"2_log_teacher" number(11),"2_dx_flow" number(11),'||
   '"3_log" number(11) default 0,"3_log_teacher" number(11),  "3_dx_flow" number(11),'||
   '"4_log" number(11) default 0, "4_log_teacher" number(11),"4_dx_flow" number(11),'||
   '"5_log" number(11) default 0,"5_log_teacher" number(11),"5_dx_flow" number(11),'||
   '"6_log" number(11) default 0, "6_log_teacher" number(11),"6_dx_flow" number(11),'||
   '"7_log" number(11) default 0, "7_log_teacher" number(11), "7_dx_flow" number(11),'||
   '"8_log" number(11) default 0,"8_log_teacher" number(11),"8_dx_flow" number(11),'||
   '"9_log" number(11),"9_log_teacher" number(11),"9_dx_flow" number(11),'||
   '"10_log" number(11),"10_log_teacher" number(11),"10_dx_flow" number(11),'||
   '"11_log" number(11), "11_log_teacher" number(11),"11_dx_flow" number(11),'||
   '"12_log" number(11), "12_log_teacher" number(11),"12_dx_flow" number(11))';
  
   -- dbms_output.put_line(sqlStr);
    sqlStr:='';
    -- execute immediate sqlStr;
    end loop;
   commit;
end;
0
0
