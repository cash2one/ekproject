PL/SQL Developer Test script 3.0
36
declare 
  cursor c_area is select abb from area;
  squenceSql varchar2(200);delSql varchar2(100);tableName varchar(30);sqlStr1 varchar2(2000);sqlStr2 varchar2(2000);sqlStr3 varchar2(2000);sqlStr4 varchar2(2000);sqlStr5 varchar2(2000);
begin
   for v_abb in c_area loop
     tableName:=v_abb.abb||'_school_use_state';
     
     sqlStr1:= 'create table '||tableName||'('|| 'id number(11),school_id  number(11),year number(6),'||' "1_log" number(11) default 0 ,"1_log_teacher" number(11) default 0,"1_dx_flow" number(11) default 0,'||
                ' "2_log" number(11) default 0,"2_log_teacher" number(11) default 0,"2_dx_flow" number(11) default 0,'||
               '"3_log" number(11) default 0,"3_log_teacher" number(11) default 0,  "3_dx_flow" number(11) default 0,'||
               '"4_log" number(11) default 0, "4_log_teacher" number(11) default 0,"4_dx_flow" number(11) default 0,';
     sqlStr2:='"5_log" number(11) default 0,"5_log_teacher" number(11) default 0,"5_dx_flow" number(11) default 0,'||
               '"6_log" number(11) default 0, "6_log_teacher" number(11) default 0,"6_dx_flow" number(11) default 0,'||
               '"7_log" number(11) default 0, "7_log_teacher" number(11) default 0, "7_dx_flow" number(11) default 0,';
     sqlStr3:='"8_log" number(11) default 0,"8_log_teacher" number(11) default 0,"8_dx_flow" number(11) default 0,'||
               '"9_log" number(11) default 0,"9_log_teacher" number(11) default 0,"9_dx_flow" number(11) default 0,';
     sqlStr4:='"10_log" number(11),"10_log_teacher" number(11) default 0,"10_dx_flow" number(11) default 0,';
               sqlStr5:= '"11_log" number(11) default 0, "11_log_teacher" number(11) default 0,"11_dx_flow" number(11) default 0,'||
               '"12_log" number(11) default 0, "12_log_teacher" number(11) default 0,"12_dx_flow" number(11) default 0)';
  
     squenceSql:='create sequence '||v_abb.abb||'_school_use_seq_id   increment   by   1   start   with   1  maxvalue   999999999';
    
     delSql:='drop table '||tableName;
     dbms_output.put_line(delSql);
     dbms_output.put_line(squenceSql);
 
     --创建表与对应的序列
     execute immediate squenceSql;
    
     --execute immediate delSql;
     execute immediate sqlStr1||sqlStr2||sqlStr3||sqlStr4||sqlStr5;
    
     
    end loop;
   commit;
end;
0
0
