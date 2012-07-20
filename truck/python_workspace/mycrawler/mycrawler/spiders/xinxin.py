#Ethanlam Test crawl web #
from scrapy.spider import BaseSpider
import os
from mycrawler.items import JingdianItem 

class XinXinSpider(BaseSpider):
    name = "XinXin"
    allowed_domains = ["cncn.com"]
    start_urls = ["http://www.cncn.com/jingdian/"]
    
    def parse(self, response):
       hxs = HtmlXPathSelector(response) 
       sites = hxs.select('//ul/li') 
       items = [] 
       for site in sites: 
           item = JingdianItem() 
           item['title'] = site.select('a/text()').extract() 
           item['link'] = site.select('a/@href').extract() 
           item['desc'] = site.select('text()').extract() 
           items.append(item) 
       return items 