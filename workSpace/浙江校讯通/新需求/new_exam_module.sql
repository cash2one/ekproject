--成绩管理的表结构


---考试信息表
create table examination_info(
   id number(10),
   name varchar2(60),
   academic_year number(6),
   term number(2),
   user_id number(8),
   school_id number(10),
   create_time date   
)


comment on table examination_info is '考试信息表(新版本)';

comment on column examination_info.academic_year is '学年';

comment on column examination_info.term is '学期，1、上学期；2、下学期';

comment on column examination_info.name is '考试名称';

comment on column examination_info.create_time is '创建时间';

comment on column examination_info.user_id is '创建人ID';

comment on column examination_info.school_id is '所属学校的ID';




CREATE SEQUENCE ZJXXT.examination_info_seq
  START WITH 1
  MAXVALUE 99999
  MINVALUE 1
  NOCYCLE
  CACHE 20
ORDER;



CREATE OR REPLACE TRIGGER ZJXXT."examination_info_TRI" 
BEFORE  INSERT ON examination_info FOR EACH ROW
BEGIN 
  SELECT examination_info_seq.NEXTVAL INTO :NEW.ID FROM DUAL; 
END;





------------------------------------------------------------------------------------

---班级成绩信息表
create table class_exam(
   id number(10),
   class_id number(10),
   subject_id number(10),
   exam_id number(10),
   score_type number(2)
)

comment on table class_exam is '班级成绩信息表(新版本)';

comment on column class_exam.id  is '学年';

comment on column class_exam.class_id  is '班级ID';

comment on column class_exam.subject_id  is '学科ID';

comment on column class_exam.exam_id  is '考试信息表 id';

comment on column class_exam.score_type  is '学科成绩计分类型 ：1、百分制，2、等级';


CREATE SEQUENCE ZJXXT.class_exam_seq
  START WITH 1
  MAXVALUE 99999
  MINVALUE 1
  NOCYCLE
  CACHE 20
ORDER;



CREATE OR REPLACE TRIGGER ZJXXT."class_exam_TRI" 
BEFORE  INSERT ON class_exam FOR EACH ROW
BEGIN 
  SELECT class_exam_seq.NEXTVAL INTO :NEW.ID FROM DUAL; 
END;



-----------------------------------------------------------------------

--成绩明细表
create table cs_score_item(
   id number(10),
   stu_sequence varchar2(30),
   classExam_id number(10),
   score number(5)
)


comment on table cs_score_item is '成绩明细表(新版本)';

comment on column cs_score_item.stu_sequence  is '学生学号';

comment on column cs_score_item.classExam_id  is '班级成绩信息ID';

comment on column cs_score_item.score  is '成绩得分';


CREATE SEQUENCE ZJXXT.cs_score_item_seq
  START WITH 1
  MAXVALUE 99999
  MINVALUE 1
  NOCYCLE
  CACHE 20
ORDER;



CREATE OR REPLACE TRIGGER ZJXXT."cs_score_item_TRI" 
BEFORE  INSERT ON cs_score_item FOR EACH ROW
BEGIN 
  SELECT cs_score_item_seq.NEXTVAL INTO :NEW.ID FROM DUAL; 
END;


----------------------------------------------------------------------------

--教师成绩点评
create table cs_exam_comment(
   id number(10),
   exam_id number(10),
   class_id number(10),
   stu_sequence varchar2(30),
   remark varchar2(300)
)

comment on table cs_exam_comment is '教师对学生的考试成绩点评记录表(新版本)';

comment on column cs_exam_comment.exam_id  is '考试ID';

comment on column cs_exam_comment.class_id  is '班级ID';

comment on column cs_exam_comment.stu_sequence  is '学生学号';

comment on column cs_exam_comment.remark  is '评语内容';


CREATE SEQUENCE ZJXXT.cs_exam_comment_seq
  START WITH 1
  MAXVALUE 99999
  MINVALUE 1
  NOCYCLE
  CACHE 20
ORDER;


CREATE OR REPLACE TRIGGER ZJXXT."cs_exam_comment_TRI" 
BEFORE  INSERT ON cs_exam_comment FOR EACH ROW
BEGIN 
  SELECT cs_exam_comment_seq.NEXTVAL INTO :NEW.ID FROM DUAL; 
END;

