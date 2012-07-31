#coding=utf-8
__author__ = 'Ethanlam'


import pymongo
connection = pymongo.Connection('localhost',27017)
db = connection.xinxin_database
print db.collection_names()


##打印所有的链接情况
def listAllLinks():
    links = db.links
    print 'desc , link ,title,parent,layer '
    for link in links.find().sort('title'):
        print '%s , %s , %s , %s , %s' % (link['desc'],link['link'],link['title'],link['parent'],link['id'])
    print '所有找到的链接情况有：%d' % links.count()

def findlinkByDesc(str):
    links = db.links
    for link in links.find({"desc": str}).sort('title'):
        if link:
             print '%s , %s , %s , %s , %s' % (link['desc'],link['link'],link['title'],link['parent'],link['id'])


def removeData():
    links = db.links
    links.remove({});
    print '对数据进行删除操作'
#    db.linlin.remove({})                   delete * from linlin
#    db.linlin.remove({'age':20})           delete linlin where age=20
#    db.linlin.remove({'age':{$lt:20}})     delete linlin where age<20
#    db.linlin.remove({'age':{$lte:20}})    delete linlin where age<=20
#    db.linlin.remove({'age':{$gt:20}})     delete linlin where age>20
#    db.linlin.remove({'age':{$gte:20}})    delete linlin where age>=20
#    db.linlin.remove({'age':{$ne:20}})     delete linlin where age!=20

listAllLinks()
findlinkByDesc('广州')
#removeData()
##listAllLinks()



