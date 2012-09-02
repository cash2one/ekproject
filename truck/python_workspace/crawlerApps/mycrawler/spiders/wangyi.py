#Ethanlam Test crawl web #
from scrapy.spider import BaseSpider
import os

class WangyiSpider(BaseSpider):
    name = "163.com"
    allowed_domains = ["163.com"]
    start_urls = ["http://www.163.com/?wd=fsd"]
    
    def parse(self, response):
        filename =  self.name+"/"+response.url.split("/")[-2] + '.html'
        _savePath = os.getcwd()+"\\"+self.name;
        print 'Current Path: %s ' %(os.getcwd()+"\\"+self.name)
        print 'crawl %s pages' %(self.name)
        if os.path.exists(_savePath)== False:
            os.makedirs(_savePath)
            print 'create dirs %s suc... ' %(_savePath)
        open(filename, 'wb').write(response.body)