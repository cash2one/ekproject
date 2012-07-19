from scrapy.spider import BaseSpider

class BaiduSpider(BaseSpider):
    name = "baidu.com"
    allowed_domains = ["baidu.com"]
    start_urls = ["http://www.baidu.com/s?wd=%CA%D6%BB%FA&inputT=2110"]
    
    def parse(self, response):
        filename = response.url.split("/")[-2] + '.html'
        open(filename, 'wb').write(response.body)