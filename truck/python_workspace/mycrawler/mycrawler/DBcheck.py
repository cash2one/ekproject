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
    for link in links.find():
        print '%s , %s , %s , %s , %s' % (link['desc'],link['link'],link['title'],link['parent'],link['layer'])
    print '所有找到的链接情况有：%d' % links.count()

def findlinkByDesc(str):
    links = db.links
    link = links.find_one({"desc": str})
    print '%s , %s , %s , %s , %s' % (link['desc'],link['link'],link['title'],link['parent'],link['layer'])

listAllLinks()
findlinkByDesc('广州')


cityName = 'http://guangzhou.cncn.com/jingdian/'
print cityName.find('guangou')