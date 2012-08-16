__author__ = 'ethan lam'
#coding=utf-8

from string import Template
import os

SQLTemplate  = Template("""

-- 老师对学生的评价得分情况 记录表
create table  zjxxt.${abb}_estimate_score(
    id  number(12),
    stu_sequence varchar(30),
    school_id   number(12),
    execute_id  number(12),
    item_cfg_id  number(12),
    rank_score   number(12),
    update_time  date  default sysdate
);


comment on table zjxxt.${abb}_estimate_score is '老师对学生的评价得分情况 记录表';

comment on column  zjxxt.${abb}_estimate_score.stu_sequence is '学生编号';

comment on column  zjxxt.${abb}_estimate_score.school_id is '学校ID';

comment on column  zjxxt.${abb}_estimate_score.execute_id is 'estimate_execute.id 评分调查活动的主键';

comment on column  zjxxt.${abb}_estimate_score.item_cfg_id is '评价条目 estimate_item_cfg ';

comment on column  zjxxt.${abb}_estimate_score.rank_score is ' 对应的得分情况 estimate_rank_cfg_id';

comment on column  zjxxt.${abb}_estimate_score.update_time is '评价更新时间';


CREATE SEQUENCE ZJXXT.${abb}_estimate_score_SEQ
  START WITH 1
  MAXVALUE 9999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER;

CREATE OR REPLACE TRIGGER "ZJXXT"."${abb}_estimate_score_TRI"
BEFORE INSERT
ON ZJXXT.${abb}_estimate_score
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN
     SELECT ZJXXT.${abb}_estimate_score_SEQ.NEXTVAL INTO :NEW.id FROM DUAL;
END;

-- 老师及其家长 对 学生的评语 记录表
create table  zjxxt.${abb}_estimate_comment(
    id  number(12),
    stu_sequence  varchar(30),
    school_id     number(12),
    execute_id   number(12),
    teacher_comment  varchar(300) ,
    parent_comment   varchar(300),
    update_time  date default sysdate
);


comment on table zjxxt.${abb}_estimate_comment is '老师对学生的评价得分情况 记录表';

comment on column  zjxxt.${abb}_estimate_comment.stu_sequence is '学生编号';

comment on column  zjxxt.${abb}_estimate_comment.school_id is '学校ID';

comment on column  zjxxt.${abb}_estimate_comment.execute_id is 'estimate_execute.id 评分调查活动的主键';

comment on column  zjxxt.${abb}_estimate_comment.teacher_comment is '教师对学生的评语';

comment on column  zjxxt.${abb}_estimate_comment.parent_comment is '教师对学生的评语';

comment on column  zjxxt.${abb}_estimate_comment.update_time is '评价更新时间';



CREATE SEQUENCE ZJXXT.${abb}_estimate_comment_SEQ
  START WITH 1
  MAXVALUE 9999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER;

CREATE OR REPLACE TRIGGER "ZJXXT"."${abb}_estimate_comment_TRI"
BEFORE INSERT
ON ZJXXT.${abb}_estimate_comment
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN
     SELECT ZJXXT.${abb}_estimate_comment_SEQ.NEXTVAL INTO :NEW.id FROM DUAL;
END;

""")


def makeCode(areaAbb):
    fieldSetContent = ''
    for abb in areaAbb:
        fieldSetContent+=SQLTemplate.safe_substitute({"abb":abb})
    return fieldSetContent


def saveFile(filePath, buf):
    if not os.path.exists(filePath):
        temp = os.path.dirname(filePath)
        if not os.path.exists(temp):
            os.makedirs(temp)
    else:
        os.remove(filePath)
    f = open(filePath,'w')
    f.write(buf)
    f.close()


areaAbb = ['qz','hz','jx','nb','sx','tz','wz','ls','jh','zs','huz']
saveFile("C:/Users/Solosus/Desktop/SqlScripts/estimate.sql",makeCode(areaAbb))