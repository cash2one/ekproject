#coding=utf-8

#Ethanlam crawl web  www.cncn.com 的目录信息 #
from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
from mycrawler.items import JingdianItem,AreaItem,CityItem


import os
import re
import pymongo
import MySQLdb


conn=MySQLdb.connect(host="localhost",user="root",passwd="123456",db="test",charset="utf8")
conn.autocommit(True)
cursor = conn.cursor()

area_dict = {}   #信息字典
urlshistory = {} #记录已经访问过的地址

#connection=pymongo.Connection('localhost',27017)
#mgdb = connection.xinxin_database



##爬虫实例(XinXin)
class XinXinSpider(BaseSpider):
    name = "XinXin"
    allowed_domains = ["cncn.com"]
    start_urls = ["http://www.cncn.com/jingdian/"]


    # 处理首页的数据
    def parse(self, response):
        ##self.cleanOldDatas();
        global area_dict
        self.downloadPage(response,'')
        hxs = HtmlXPathSelector(response)
        # fetch entry of urls that the citys of view places
        links  = hxs.select('//div[@class="topM mr10"]/a[contains(@href, @class)]')
        items = []
        nextUrls = []  # new url
        for index, link in enumerate(links):
            args = (index+1, link.select('@href').extract(), link.select('@class').extract(),link.select('text()').extract())
            item = AreaItem()
            item['name'] = args[2][0]
            item['link_url'] = args[1][0]
            item['description'] = args[3][0]
            item['id'] = args[0]
            area_dict[item['name']+'_id'] = args[00]  ##建立爬虫主键
            area_dict[item['name']+'_name'] = args[3][0]
            ##str_to = args[2].decode('gbk', 'ignore').encode('utf-8')
            items.append(item)

#            if args[1][0].find('guangdong')>= 0:
#                nextUrls.append(args[1][0])

            self.saveToDB(item)
            nextUrls.append(args[1][0])
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
        parentId = area_dict[areaName+'_id']
        nextUrls = []  # new url
        for index, link in enumerate(links):
            if len(re.findall(r'hongkong|macao|taiwan',areaName)):
                continue
            args = (index+1, link.select('@href').extract(),link.select('text()').extract())
            ##print args
            item = CityItem()
            item['link_url'] = args[1][0]
            item['area_id'] = parentId
            item['id'] = (parentId*30)+ args[0]
            if args[2] :
                item['name'] = args[2][0]
                item['description'] = args[2][0]
            if item["link_url"].find('.cncn.com')>=0:
                item['name'] = item['link_url'].split("/")[2].replace('.cncn.com','')
                area_dict[item['name']+"_pname"] = areaName
                area_dict[item['name']+"_pid"] = parentId
                if area_dict.get(item['name']+"_id") == None:
                    area_dict[item['name']+"_id"] = item['id']
                    area_dict[item['name']+"_name"] = item['description']
                nextUrls.append(item['link_url'])
            if item.get('name')  is None:
                item['name'] = "unkown"
            #保存到数据库
            self.saveToDB(item)
            items.append(item)
            ##items.extend([self.make_requests_from_url(url).replace(callback=self.parse_cityIndexPage) for url in nextUrls])
        items.extend([self.make_requests_from_url('http://'+it['name']+'.cncn.com/jingdian/index1.htm').replace(callback=self.parse_cityJingDianListPages) for it in items])
        ##yield Request(url, meta={'item': item}, callback=self.parse_item)
        return items



    #转到城市的对应的景点的分页数据情况
    def parse_cityJingDianListPages(self,response):
        global area_dict
        baseUrlStr = "http://"+response.url.split("/")[2]+"/jingdian/"
        cityName = response.url.split("/")[2].replace('.cncn.com','')
        areaName = area_dict[cityName+"_pname"]
        items = []
        if True:
#            pass
#        else:
            self.downloadPage(response,areaName)
            #打印出景点具体的分页情况
            hxs = HtmlXPathSelector(response)
            links  = hxs.select('//div[@class="recommend1 mt10"]/div[@class="cutpage"][1]/div[@class="cp2"]/li/a')
            #print '%s JingDianListPages: ' % cityName
            for index, link in enumerate(links):
                args = (index+1, link.select('@href').extract(),link.select('text()').extract())
                items.extend([self.make_requests_from_url(baseUrlStr+link.select('@href').extract()[0]).replace(callback=self.parse_JingDianEntryLinkPage)])
                #print baseUrlStr+link.select('@href').extract()[0]
        return items



    ##通过分页提取景点列表入口地址
    def parse_JingDianEntryLinkPage(self,response):
        global urlshistory
        global area_dict
        cityName = response.url.split("/")[2].replace('.cncn.com','')
        #取得分页编码,用来编辑对应的景点ID
        pageSeqNum = response.url.split("/")[4].replace('index','').replace('.htm','')

        if self.hasAccessUrl(cityName+"_"+pageSeqNum):
            #假如已经访问过了，就不做重复处理
            pass
        else:
            baseUrl = response.url.split("/")[2]
            hxs = HtmlXPathSelector(response)
            parentId = area_dict[cityName+"_id"]
            #得出景点的链接入口地址
            items = []
            links  = hxs.select('//div[@class="txt mt10"]/dl[@class="gPopN"]/dd/a')
            for index, link in enumerate(links):
                args = (index+1, link.select('@href').extract(),link.select('text()').extract())
                print "%s jingdian: %s %s " % (cityName,args[2][0],"http://"+baseUrl+args[1][0])
                item = JingdianItem()
                item['id'] = "J-"+str(parentId)+"-"+pageSeqNum+"-"+str(args[0])
                if args[1][0]:
                    jingdianName = args[1][0].split("/")[2]
                    item['name'] = jingdianName
                    area_dict[jingdianName+"_id"] = item['id']
                item['description'] = args[2][0]
                item['link_url'] = "http://"+baseUrl+args[1][0]
                item['city_id'] = parentId
                #item['type'] = 1
                items.append(item)
                #保存景点信息
                self.saveToDB(item)
                items.extend([self.make_requests_from_url(item['link_url']+'/profile').replace(callback=self.parse_JingDianProfilePage)])
            return items





    #景点的简介信息
    def parse_JingDianProfilePage(self,response):
        global area_dict
        cityName = response.url.split("/")[2].replace('.cncn.com','')
        jingdianName = response.url.split("/")[4]
        hxs = HtmlXPathSelector(response)
        contentTxt =""
        type = 1

        #正文内容是样式1的
        contents  = hxs.select('//div[@id="article"]').extract()
        for content in contents:
            contentTxt +=content
        #正文内容是样式2 的
        if contentTxt =='':
            contents  = hxs.select('//div[@class="sideL"]').extract()
            contentTxt =""
            type = 2
            for content in contents:
                contentTxt +=content

        item = JingdianItem()
        item['profile'] = contentTxt
        item['link_url'] = response.url
        item['city_id'] = area_dict[cityName+"_id"]
        item['type'] = type
        item['id'] = area_dict[jingdianName+"_id"]
        self.saveToDB(item)
        return [item]



    #判断该地址是否已经被处理过
    def hasAccessUrl(self,url):
        global urlshistory
        if urlshistory.get(url):
            return True
        else:
            urlshistory[url] = 1
            return False


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
    #    def saveToDB(self,item):
    #        global db
    #        if item["type"] == 1:
    #            area = {'id':item['id'],'title':item['title'],'parent':item['parent'],'link':item['link'],'desc':item['desc']}
    #            areas = mgdb.areas
    #            areas.insert(area)
    #        if item["type"] == 2:
    #            city = {'id':item['id'],'title':item['title'],'parent':item['parent'],'link':item['link'],'desc':item['desc']}
    #            citys = mgdb.citys
    #            citys.insert(city)
    #        if item["type"] == 3:
    #            jingdian = {'id':item['id'],'title':item['title'],'parent':item['parent'],'link':item['link'],'desc':item['desc']}
    #            jingdians = mgdb.jingdians
    #            jingdians.insert(jingdian)
    #        if item["type"] == 4:
    #            jdetail = {'id':item['id'],'title':item['title'],'parent':item['parent'],'link':item['link'],'desc':item['desc']}
    #            jdetails = mgdb.jdetails
    #            jdetails.insert(jdetail)


    def cleanOldDatas(self):
        sql = "delete from area;"
        cursor.execute(sql)
        sql = "delete from city;"
        cursor.execute(sql)
        sql = "delete from jingdian;"
        cursor.execute(sql)
        print 'clean old data....'

    #入库处理
    def saveToDB(self,item):
        if isinstance(item,AreaItem):
            #录入地区信息
            sql = "insert into area(id,name,description,link_url) values(%s,%s,%s,%s)"
            param = (item['id'],item['name'],item['description'],item['link_url'])
            cursor.execute(sql,param)
        if isinstance(item,CityItem):
            if item.get('description') is None:
                item['description'] = 'unkown'
            #录入城市信息
            sql = "insert into city(id,name,description,link_url,area_id) values(%s,%s,%s,%s,%s)"
            param = (item['id'],item['name'],item['description'],item['link_url'],item['area_id'])
            cursor.execute(sql,param)
        if isinstance(item,JingdianItem):
            if item.get('type') is None:
                #新增景点信息
                sql = "insert into jingdian(id,name,description,link_url,city_id) values(%s,%s,%s,%s,%s)"
                param = (item['id'],item['name'],item['description'],item['link_url'],item['city_id'])
                cursor.execute(sql,param)
            elif  item.get('profile') is not None:
                #更新记录
                sql = "update jingdian set profile = %s,type= %s where id = %s "
                param = (item['profile'],item['type'],item['id'])
                cursor.execute(sql,param)

