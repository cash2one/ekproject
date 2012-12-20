__author__ = 'ethan lam'
#coding=utf-8

from string import Template
import os



SQLTemplate  = ("""
    insert into zjxxt.${areaAbb}_KQ_ATTREC
    select 1,12,t.school_id,t.class_id,t.ic_no,t.STU_SEQUENCE,sysdate+${num}-8/24,sysdate+${num}-8/24,1,1,stu_no,1,t.stu_type
    from (select stu.STU_SEQUENCE,sc.school_id,STU.STU_NO,stu.stu_type,stu.ic_no,sc.class_id from zjxxt.${areaAbb}_xj_student stu,zjxxt.${areaAbb}_xj_stu_class sc
    where stu.STU_SEQUENCE = sc.STU_SEQUENCE and stu.ic_no is not null
    and sc.class_id = 141901)t;
""")




#读取文本类内容
def readCodeTemplate(filePath):
    if not os.path.exists(filePath):
        print ' [出错提示]：模版文件不存在，请检查文件的路径...'
        return

    #开始读写文件
    SQLTemplateContent = ''
    _templateFile = open(filePath)
    for line in _templateFile :
        SQLTemplateContent += line
        pass
    print ' [执行信息]：已经读取了对应的模版文件：\n%s\n' % SQLTemplateContent
    return SQLTemplateContent




#生成模版内容
def makeCode(paramsSet,templateFile):
    fieldSetContent = ''
    #假设是从文件中加载模版
    if templateFile is not None :
        fieldSetContent = readCodeTemplate(templateFile)
    

    if fieldSetContent is None or ''== fieldSetContent: 
        print ' [出错提示]：没有读取到任何的模版内容!'
        return None

    #模版内容不为空
    fieldSetContent = SQLTemplate

    for paramName in paramsSet:
        if paramsSet.get(paramName) is not None:
            print  ' [执行信息]：开始进行替换对应的参数 param: %s : %s' % (paramName,paramsSet[paramName])
            fieldSetContent = replaceContentByParams(fieldSetContent,paramName,paramsSet[paramName])
            #print  'Template replace Result: %s ' % fieldSetContent
    return fieldSetContent




#进行替换操作
def replaceContentByParams(templateContent,paramName,paramList):
    nt= Template(templateContent)
    fieldSetContent = ''
    for val in paramList:
        fieldSetContent+=nt.safe_substitute({paramName:val})
        #print 'replaceContentByParams %s' % fieldSetContent
    return fieldSetContent




#模版结果文件
def saveFile(filePath, buf):

    if buf is None:
        print ' [出错提示]：没有生成任何的结果文件，原因是输入的模版为空'
        return

    if not os.path.exists(filePath):
        temp = os.path.dirname(filePath)
        if not os.path.exists(temp):
            os.makedirs(temp)
    else:
        os.remove(filePath)

    f = open(filePath,'w')
    f.write(buf)
    f.close()
    print ' [执行信息]：已经执行完毕! 结果保存路径为：%s ' % filePath
    pass


#入口程序
def main():
    params = {"areaAbb":['cs','hz'],"num":{1,2,3}}
    templateFile = 'd:/test.template'
    saveFile("d:/test_1.sql",makeCode(params,templateFile))
    pass



#主程序
if __name__ == '__main__':
    main()


