#coding=utf-8

#Ethanlam crawl web  www.cncn.com 的目录信息 #
from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
from mycrawler.items import JingdianItem 

import os


area_dict = {}


##爬虫实例(XinXin)
class XinXinSpider(BaseSpider):
    name = "XinXin"
    allowed_domains = ["cncn.com"]
    start_urls = ["http://www.cncn.com/jingdian/"]


    # 处理首页的数据
    def parse(self, response):
       global area_dict
       self.downloadPage(response,'')
       hxs = HtmlXPathSelector(response)
       # fetch entry of urls that the citys of view places  
       links  = hxs.select('//div[@class="topM mr10"]/a[contains(@href, @class)]') 
       items = []
       nextUrls = []  # new url
       for index, link in enumerate(links):
           args = (index, link.select('@href').extract(), link.select('@class').extract(),link.select('text()').extract())
           item = JingdianItem() 
           item['title'] = args[2][0]
           item['link'] = args[1][0] 
           item['desc'] = args[3][0]
           item['layer'] = "1:"+str(args[0])
           item['parent'] = -1
           area_dict[item['title']] = index  ##建立爬虫主键
           ##str_to = args[2].decode('gbk', 'ignore').encode('utf-8')
           items.append(item)
           ##if args[1][0].find('guangdong')>= 0:
           nextUrls.append(args[1][0])
           ##nextUrls.append(args[1][0])
       ##crawl the next url page
       items.extend([self.make_requests_from_url(url).replace(callback=self.parse_areaIndexPage) for url in nextUrls])    
       return items




    #处理各省的首页
    def parse_areaIndexPage(self,response):
       global area_dict
       areaName = response.url.split("/")[2].replace('.cncn.com','')
       self.downloadPage(response,'areas')
       hxs = HtmlXPathSelector(response)
       #得出各省的对应城市目录情况
       links  = hxs.select('//div[@class="txt"]/dl[1]/dd/a')
       items = []
       parentId = area_dict[areaName]
       nextUrls = []  # new url
       for index, link in enumerate(links):
           args = (index, link.select('@href').extract(),link.select('text()').extract())
           ##print args
           item = JingdianItem()
           item['link'] = args[1][0]
           item['parent'] = parentId
           if args[2] :
               item['title'] = args[2][0]
               item['desc'] = args[2][0]
           if item["link"].find('.cncn.com')>=0:
               item['title'] = item['link'].split("/")[2].replace('.cncn.com','')
               area_dict[item['title']] = areaName
               nextUrls.append(item['link'])
           item['layer'] = "2:"+str(parentId)+"-"+str(args[0])
           items.append(item)
       items.extend([self.make_requests_from_url(url).replace(callback=self.parse_cityIndexPage) for url in nextUrls])   
       return items


    def parse_cityIndexPage(self,response):
       global area_dict
       cityName = response.url.split("/")[2].replace('.cncn.com','')
       areaName = area_dict[cityName]
       self.downloadPage(response,areaName)
       print ''






    #下载对应的页面
    def downloadPage(self,response,layer):
        rootPath = self.name+"/"
        parentPath = rootPath
        if layer == 'areas' : 
            filename =  parentPath+"/"+response.url.split("/")[2].replace('.cncn.com','') + '.html'
        elif layer !='':
            parentPath = parentPath +"/"+ layer
            filename =  parentPath+"/"+response.url.split("/")[2].replace('.cncn.com','') + '.html'
        else:
            filename =  parentPath+"/"+response.url.split("/")[2] + '.html'
        _savePath = os.getcwd()+"\\" + parentPath
        if os.path.exists(_savePath)== False:
            os.makedirs(_savePath)
            print 'create dirs %s suc... ' %(_savePath)
        open(filename, 'wb').write(response.body)
        