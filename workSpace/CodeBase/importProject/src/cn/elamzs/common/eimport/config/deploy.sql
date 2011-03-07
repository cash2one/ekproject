
--记录导入任务状态的表
create table  eimport_task_status(
      handler_id varchar2(30),
      file_name varchar2(30),
      src_path varchar2(200),
      result_path varchar2(200),
      state number(3),
      TASK_TYPE varchar2(300),
      start_time Date,
      finish_time Date 
)


-- Sequence
create sequence TASK_HANDLEID_AUTOINC 
minvalue 1 
start with 1 
increment by 1 
nocache;



-- 触发器
create or replace trigger INSERT_FOR_AUTOINC
   before insert on eimport_task_status  
   for each row 
declare 
  -- local variables here
begin 
    select TASK_HANDLEID_AUTOINC.nextval into:new.handler_id from dual;
end insert_for_autoinc;