__author__ = 'ethan lam'
#coding=utf-8

from string import Template
import os

SQLTemplate  = Template("""

insert into zjxxt.hz_KQ_ATTREC
select 1,12,t.school_id,t.class_id,t.ic_no,t.STU_SEQUENCE,sysdate+${num}-8/24,sysdate+${num}-8/24,1,1,stu_no,1,t.stu_type
from (select stu.STU_SEQUENCE,sc.school_id,STU.STU_NO,stu.stu_type,stu.ic_no,sc.class_id from zjxxt.hz_xj_student stu,zjxxt.hz_xj_stu_class sc
where stu.STU_SEQUENCE = sc.STU_SEQUENCE and stu.ic_no is not null
and sc.class_id = 141901)t;

insert into zjxxt.hz_KQ_ATTREC
select 1,12,t.school_id,t.class_id,t.ic_no,t.STU_SEQUENCE,sysdate+${num}-4/24,sysdate+${num}-4/24,2,1,stu_no,1,t.stu_type
from (select stu.STU_SEQUENCE,sc.school_id,STU.STU_NO,stu.stu_type,stu.ic_no,sc.class_id from zjxxt.hz_xj_student stu,zjxxt.hz_xj_stu_class sc
where stu.STU_SEQUENCE = sc.STU_SEQUENCE and stu.ic_no is not null
and sc.class_id = 141901)t;

insert into zjxxt.hz_KQ_ATTREC
select 1,12,t.school_id,t.class_id,t.ic_no,t.STU_SEQUENCE,sysdate+${num}-2/24,sysdate+${num}-2/24,3,1,stu_no,1,t.stu_type
from (select stu.STU_SEQUENCE,sc.school_id,STU.STU_NO,stu.stu_type,stu.ic_no,sc.class_id from zjxxt.hz_xj_student stu,zjxxt.hz_xj_stu_class sc
where stu.STU_SEQUENCE = sc.STU_SEQUENCE and stu.ic_no is not null
and sc.class_id = 141901)t;

insert into zjxxt.hz_KQ_ATTREC
select 1,12,t.school_id,t.class_id,t.ic_no,t.STU_SEQUENCE,sysdate+${num}+1/24,sysdate+${num}+1/24,4,1,stu_no,1,t.stu_type
from (select stu.STU_SEQUENCE,sc.school_id,STU.STU_NO,stu.stu_type,stu.ic_no,sc.class_id from zjxxt.hz_xj_student stu,zjxxt.hz_xj_stu_class sc
where stu.STU_SEQUENCE = sc.STU_SEQUENCE and stu.ic_no is not null
and sc.class_id = 141901)t;


""")


def makeCode(areaAbb):
    fieldSetContent = ''
    for abb in areaAbb:
        fieldSetContent+=SQLTemplate.safe_substitute({"num":abb})
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


areaAbb = [0,1,2,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21]
saveFile("d:/kq_data.sql",makeCode(areaAbb))