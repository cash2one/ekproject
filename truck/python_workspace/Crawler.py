#ethan lam  crawler
import urllib2
import time
import redis

r = redis.StrictRedis(host='61.142.114.242', port=6379, db=0)
main_key = "crawl:url:"

class Crawler(object):
    def __init__(self,urls):
        self.urls = urls
        self.dirRoot = "d:/temp/crawl_web/"

    def save(self,url,content):
        _fileName = url.replace('http://','')
        _fileName = _fileName.replace('/','')
        _fileName = _fileName +".txt"
        print 'start to  save:'+url+" At:%d" %(time.time()) 
        _file = open(self.dirRoot+_fileName,'w')
        _file.write(content)
        _file.close()
        r.set(main_key+url,time.time())
        print 'start to  save:'+url+" At:%d" %(time.time()) 


    def start(self):
        for url in self.urls:
            ##(time.strftime(%Y-%m-%d %H:%M:%S,time.localtime(time.time()))) 
            print 'start to crawl:'+url+" At:%d" %(time.time())    
            content = urllib2.urlopen(url).read()
            self.save(url,content)
            print 'finished to crawl:'+url+" At:%d" %(time.time())  
            




test = Crawler(["http://www.sina.com.cn","http://www.163.com","http://www.sohu.com"])
test.start()


        
        
    
