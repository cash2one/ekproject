#coding=utf-8
__author__ = 'ethanlam'


import redis
hostIP = "192.168.4.39"

#打印数据
def showRangeDatas(hostIP,nport):
    r = redis.Redis(host=hostIP, port=nport, db=0)
    for i in range(1,1001):
        print r.get("Test:"+str(i))


def showCurrentMaster(hostIp,nport):
    r = redis.Redis(host=hostIP, port=nport, db=0)
    print 'MASTER_NODE %s ' % r.get('MASTER_NODE')
    print 'MASTER_NODE_NEXT %s ' % r.get('MASTER_NODE_NEXT')
    print 'REDIS_NODES_CFG %s ' % r.get('REDIS_NODES_CFG')

#
#showRangeDatas(hostIP,6379);

showCurrentMaster(hostIP,6379);


