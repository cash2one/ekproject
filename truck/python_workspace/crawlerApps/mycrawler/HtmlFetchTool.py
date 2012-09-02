__author__ = 'Ethanlam'


from scrapy.selector import HtmlXPathSelector
import urllib2
response = urllib2.urlopen('http://guangzhou.cncn.com//jingdian/index1.htm')
print response
#hxs = HtmlXPathSelector(response)
#links  = hxs.select('//div[@class="txt"]/dl[@class="gPopN"]')