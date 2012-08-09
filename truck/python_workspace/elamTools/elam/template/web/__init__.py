__author__ = 'Ethanlam'

from elam.template.web.EntityTemplate import FieldSet
from elam.template.web.EntityTemplate import GetAndSetMethod
from elam.template.web.EntityTemplate import EntityTemplate

def makeCode(fieldSet):
    fieldSetContent = ''
    GetAndSetContent = ''
    for filed in fieldSet:
        fieldSetContent+=FieldSet.safe_substitute({"fieldName":filed[0],"fieldType":filed[1]})
        GetAndSetContent+= GetAndSetMethod.safe_substitute({"fieldName":filed[0],"FieldName":filed[0].capitalize(),"fieldType":filed[1]})
    entityContent =  EntityTemplate.safe_substitute({"ClassName":"Test","Author":"Ethan.Lam","Datetime":"2012-01","fieldSet":fieldSetContent,"GetAndSet":GetAndSetContent})
    return entityContent


fieldSet =[["id","int"],["name","String"],["data","long"]]
print makeCode(fieldSet)