#coding=utf-8

#Ethanlam crawl web  www.cncn.com 的目录信息 #
from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
from mycrawler.items import JingdianItem 


import os
import re
import pymongo

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
           if args[1][0].find('guangdong')>= 0:
              nextUrls.append(args[1][0])
           self.saveToMongoDB(item)
           ##nextUrls.append(args[1][0])
       ##crawl the next url page
       items.extend([self.make_requests_from_url(url).replace(callback=self.parse_areaIndexPage) for url in nextUrls])
       ##items.extend([Request(url, meta={'item': item}, callback=self.parse_areaIndexPage) for url in nextUrls ])
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
           if len(re.findall(r'hongkong|macao|taiwan',areaName)):
               continue
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
           self.saveToMongoDB(item)
           items.append(item)
       ##items.extend([self.make_requests_from_url(url).replace(callback=self.parse_cityIndexPage) for url in nextUrls])
       items.extend([self.make_requests_from_url('http://'+it['title']+'.cncn.com/jingdian/index1.htm').replace(callback=self.parse_cityJingDianListPages) for it in items])
       ##yield Request(url, meta={'item': item}, callback=self.parse_item)
       return items


    #转到城市的对应的景点的分页数据情况
    def parse_cityJingDianListPages(self,response):
       global area_dict
       baseUrlStr = "http://"+response.url.split("/")[2]+"/jingdian/"
       cityName = response.url.split("/")[2].replace('.cncn.com','')
       areaName = area_dict[cityName]
       if cityName.find('guangzhou')< 0:
            pass
       else:
           self.downloadPage(response,areaName)
           items = []
           #打印出景点具体的分页情况
           hxs = HtmlXPathSelector(response)
           links  = hxs.select('//div[@class="recommend1 mt10"]/div[@class="cutpage"][1]/div[@class="cp2"]/li/a')
           print '%s JingDianListPages: ' % cityName
           for index, link in enumerate(links):
               args = (index, link.select('@href').extract(),link.select('text()').extract())
               ##items.extend([self.make_requests_from_url(baseUrlStr+link.select('@href').extract()[0]).replace(callback=self.parse_JingDianPage)])
               print baseUrlStr+link.select('@href').extract()[0]


    def parse_JingDianPage(self,response):
        print 'parse_JingDianPage:%s' % response.url




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




    #保存到数据库中
    def saveToMongoDB(self,item):
        pass
        connection=pymongo.Connection('localhost',27017)
        db = connection.xinxin_database
        link = {'layer':item['layer'],'title':item['title'],'parent':item['parent'],'link':item['link'],'desc':item['desc']}
        links = db.links
        links.insert(link)
        