#Ethanlam Test crawl web #
from scrapy.spider import BaseSpider
import os

class BaiduSpider(BaseSpider):
    name = "baidu.com"
    allowed_domains = ["baidu.com"]
    start_urls = ["http://www.baidu.com/s?wd=%CA%D6%BB%FA&inputT=2110"]
    
    def parse(self, response):
        filename =  self.name+"/"+response.url.split("/")[-2] + '.html'
        _savePath = os.getcwd()+"\\"+self.name;
        print 'Current Path: %s ' %(os.getcwd()+"\\"+self.name)
        print 'crawl %s pages' %(self.name)
        if os.path.exists(_savePath)== False:
            os.makedirs(_savePath)
            print 'create dirs %s suc... ' %(_savePath)
        open(filename, 'wb').write(response.body)